package com.cuning.controller.order;


import com.cuning.service.order.SeckillService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api(tags = "订单模块")
@RequestMapping("/order")
@Slf4j
public class OrderController {

    /**
     * 创建订单 需要递：购物车信息，用户信息
     */

    @Autowired
    private SeckillService seckillService;

    @PostMapping("/seckillBuy")
    @ApiOperation(value = "开始秒杀", notes = "有序秒杀抢购")
    public RequestResult<String> seckillBuy(@RequestParam String userId,
                                            @RequestParam String prodId,
                                            @RequestParam Integer buyCount) {

        // TODO 增加请求许可校验，参考token自定义注解，校验抢购用户权限(必须是已登录授权状态)
        // TODO 增加对请求参数的校验，用户是否正确，商品信息是否正确（根据编号获取商品信息）


        // 如果瞬时请求用户过多，可以借助redis的incr原子操作，计数，超出设定请求数量（多出库存数量），直接返回失败（当前请求用户过多，请稍后重试）
        if (seckillService.limitByUserCount(prodId)) {
            return ResultBuildUtil.fail("301", "抢购失败，请求用户过多");
        }

        log.info("--- 开始抢购，当前用户：{}，当前商品：{}，购买数量：{} ---", userId, prodId, buyCount);

        // TODO 提高抢购入口的并发处理能力，必须要减少数据库交互，可以设计为根据商品编号，先从redis缓存中查询商品，如果信息存在，则直接参与抢购
        // TODO 极端情况：redis无法使用，必须增加redis的高可用，确保redis永远有效，考虑的模式就是集群模式下的哨兵机制

        // 获取商品库存数量(模拟将商品库存放入redis中，实际业务中，是在后台预热抢购商品时，同步的)
        seckillService.initProdStock2Redis(prodId);

        // 校验商品库存是否充足，如果够，进行抢购，如果不足，抢购失败
        log.info("--- 1 抢购商品：{}，库存是否充足 ---", prodId);

        if (!seckillService.checkProdStockEnough(prodId, buyCount)) {
            return ResultBuildUtil.fail("302", "抢购失败，库存不足！");
        }

        // 增加限流处理，锁定当前抢购用户，一个用户，只能抢一次，加锁，锁整个活动时间
        log.info("---- 2.校验抢购用户：{}，是否已经参与过当前商品的抢购 ----", userId);
        if (seckillService.checkSeckillUserBought(userId, prodId)) {
            return ResultBuildUtil.fail("303", "抢购失败，不能重复抢购！");
        }

        // 开始进行抢购，给商品库存上锁（上锁成功，可以继续购买，如果上锁失败，说明有人已经在抢购，继续等待库存释放）
        log.info("--- 3 校验库存锁，用户：{}，商品：{}，库存上锁 ---", userId, prodId);
        if (seckillService.checkProdStockLocked(prodId)) {
            // 使用死循环，等待库存锁释放
            while (true) {
                // 不停的尝试锁库存，如果锁库成功，代表库存锁已经被释放，可以继续购买，如果锁库存失败，代表有人抢购，继续等待被释放
                if (!seckillService.checkProdStockLocked(prodId)) {
                    log.info("--- 4.商品{}，库存上锁成功 ---");
                    break;
                }
                log.info("--- 4 用户：{}，商品：{}，库存上锁失败，等待释放 ---", userId, prodId);
            }
        }

        // 扣减库存，如果库存扣减失败，（并发情况下，多个用户同时库存充足，进入抢购，但是库存只够部分用户购买），返回失败
        if (seckillService.subProdStock(prodId, buyCount)) {
            // 库存已经不足，释放库存锁
            seckillService.unlockProdStock(prodId);
            return ResultBuildUtil.fail("302", "抢购失败!库存不足");
        }

        // 调用订单中心的业务接口，生成订单返回
        log.info("--- 5 用户：{}，商品：{}，调用订单接口，开始下单 ---", userId, prodId);

        String tradeOrderNo = seckillService.createOrder(userId, prodId, buyCount);

        log.info("--- 6 用户：{}，商品：{}，调用订单接口，下单成功，订单号：{} ---", userId, prodId, tradeOrderNo);


        // 生成订单成功，立刻释放库存锁，其他用户继续抢购
        seckillService.unlockProdStock(prodId);

        // 返回抢购成功
        return ResultBuildUtil.success("抢购成功,抢购订单号: " + tradeOrderNo);
    }



    @GetMapping("/getOrderInfo")
    @ApiOperation(value = "获取订单信息", notes = "获取订单状态")
    public RequestResult<Map<String, String>> seckillGetOrderInfo(@RequestParam String tradeOrderNo, @RequestParam String userId) {
        //todo 当抢购成功后，前端提示：抢购成功，正在排队，同时前端会发起查询订单，确认订单是否生成成功，一旦订单生成成功，跳转到支付
        //调用业务接口，查询订单详情（订单在订单中心）
        Map<String, String> orderMap = seckillService.getOrderNoInfo(userId, tradeOrderNo);
        return ResultBuildUtil.success(orderMap);
    }

    @ApiOperation(value = "微信订单支付")
    @GetMapping("/seckillPayOrder")
    public RequestResult<Map<String, String>> secKillPayOrderInfo(@RequestParam String userId, @RequestParam String prodId,
                                                                  @RequestParam String tradeOrderNo) {

        //调用业务接口，支付订单（订单数据唉订单中心，保存了订单的商品，价格等信息，支付要先到订单中心，由订单中心发起对账务支付的调用）
        Map<String,String> payOrderMap = seckillService.payOrder(userId, prodId, tradeOrderNo);

        return ResultBuildUtil.success(payOrderMap);

    }

    @ApiOperation(value = "支付宝订单支付")
    @GetMapping("/aliSeckillPayOrder")
    public String aliSecKillPayOrderInfo(@RequestParam String userId, @RequestParam String prodId,
                                                                  @RequestParam String tradeOrderNo) {

        //调用业务接口，支付订单（订单数据唉订单中心，保存了订单的商品，价格等信息，支付要先到订单中心，由订单中心发起对账务支付的调用）
        String payOrderMap = seckillService.AliPayOrder(userId, prodId, tradeOrderNo);

        return payOrderMap;

    }
}
