package com.cuning.controller;

import com.cuning.service.TradeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 订单中心业务入口
 */
@Slf4j
@RestController
public class TradeOrderController {
    @Autowired
    private TradeOrderService tradeOrderService;

    //生成订单交易入口
    @PostMapping("/createTradeOrder")
    public String createTradeOrder(@RequestParam String userId,
                                   @RequestParam String prodId,
                                   @RequestParam Integer buyCount) {
        //
        log.info("------ 订单中心，开始生成交易订单，用户：{}，商品：{}，购买数量：{}", userId, prodId, buyCount);
        //Todo 下单接口，需要做所有参数的校验，判断用户是否存在，数据是否正常，商品数是否正常，当前抢购的商品是否在抢购的活动中
        //TODO 订单中心所有的数据来源都不能直接信任接口参数，而是都要从数据库或者缓存中获取（尤其是商品的价格）
        //TODO 所有的业务校验结束，需要封装订单实体，进行意不生成订单（用户不关心的业务，可以使用异步消息队列，订单入库）

        //模拟生成订单编号，封装到订单（使用map代替）
        String tradeOrderNo = "K" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + UUID.randomUUID().toString().substring(0, 5);

        //模拟创建订单实体，发送异步消息队列，生成订单入库
        Map<String, String> tradeOrder = new HashMap<>();
        tradeOrder.put("userId", userId);
        tradeOrder.put("prodId", prodId);
        tradeOrder.put("buyCount", buyCount.toString());
        tradeOrder.put("tradeOrderNo", tradeOrderNo);

        // 发送生成订单请求到消息队列中
        tradeOrderService.saveTradeOrder(tradeOrder);
        log.info("------ 订单中心，生成交易订单成功，用户：{}，商品：{}，购买数量：{}", userId, prodId, buyCount);


        //返回交易订单号
        return tradeOrderNo;
    }

    @GetMapping("/queryTradeOrder")
    public Map<String, String> secKillQueryOrder(@RequestParam String userId, @RequestParam String orderId) {
        Map<String, String> orderMap = tradeOrderService.findTradeOrder(userId, orderId);

        return orderMap;
    }

    @GetMapping("/payTradeOrder")
    public Map<String, String> secKillPayOrder(@RequestParam String userId, @RequestParam String prodId, @RequestParam String orderId) throws Exception {
        Map<String, String> orderMap = tradeOrderService.payTradeOrder(userId, prodId, orderId);

        return orderMap;
    }

    @GetMapping("/aliPayTradeOrder")
    public String secKillAliPayOrder(@RequestParam String userId, @RequestParam String prodId, @RequestParam String orderId) throws Exception {
        String orderMap = tradeOrderService.aliPayTradeOrder(userId, prodId, orderId);

        return orderMap;
    }
}
