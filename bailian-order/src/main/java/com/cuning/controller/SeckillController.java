package com.cuning.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cuning.bean.seckill.BailianSeckill;
import com.cuning.bean.seckill.BailianSeckillUser;
import com.cuning.bean.shoppingOrder.BailianOrder;
import com.cuning.bean.user.User;
import com.cuning.service.SeckillService;
import com.cuning.service.SeckillUserService;
import com.cuning.util.RedisUtils;
import com.cuning.vo.SeckillVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created On : 2022/6/16.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: SeckillController
 */
@RestController
@Api(tags = "抢购操作入口")
public class SeckillController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private SeckillUserService seckillUserService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/insertShop")
    @ApiOperation(value = "插入抢购商品")
    public Boolean insertShop(@RequestBody BailianSeckill bailianSeckill){
        return seckillService.insertSeckillShop(bailianSeckill);
    }

    @PostMapping("/deleteShop")
    @ApiOperation(value = "删除抢购商品")
    public Boolean deleteShop(@RequestParam String goodsId){
        return seckillService.deleteSeckillShop(goodsId);
    }

    @PostMapping("/updateShop")
    @ApiOperation(value = "修改抢购商品")
    public Boolean updateShop(@RequestBody BailianSeckill bailianSeckill){
        return seckillService.updateSeckillShop(bailianSeckill);
    }

    @GetMapping("/selectShop")
    @ApiOperation(value = "搜索抢购商品")
    public BailianSeckill selectShop(@RequestParam String goodsName){
        return seckillService.selectSeckillShop(goodsName);
    }

    // 传入 多个商品 和一个用户
    // 循环商品id列表
    // 存入redis
    // 假设redis已经存在 作出判断
    // 商品id 对应的key固定 用户每次判断然后插入
    // 多个key对应多个用户信息
    // 同时获取多个key 进行降重 每个时段有一批key
    // 一个总id管理一批key id
    // 过期时间设置 总id 还是 key对应的用户信息
    @GetMapping("/registerSeckill")
    @ApiOperation(value = "抢购登记 接着发布抢购通知")
    public String registerSeckill(@RequestBody SeckillVO seckillVO){
        // 商品id 分开存
        // 一个商品对应多个用户
        String goodsId =seckillVO.getSeckill().getGoodsId();
        // userList去重
        List<User> userNewList = seckillVO.getUserList().stream().collect(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(User::getUserId))
        )).stream().collect(Collectors.toList());
        // 取出id
        List<String> userIds = userNewList.stream().map(User::getUserId).collect(Collectors.toList());
        // 判断是否是新 id
        if (ObjectUtils.isEmpty(redisUtils.get(goodsId))){
            // 不存在存入 list key: 商品id 值是: userId的集合 过期时间
            // 计算到期时间 前五分钟
            long expireTime = (seckillVO.getSeckill().getGoodsStartTime().getTime() - (new Date().getTime()) - 5*60*1000) / 1000L;
            redisUtils.lSet(goodsId,userIds);
            // 设置另一 相关goodsId的key 有过期时间
            // 因为过期后无法获取value值 加前缀 sign:
            redisTemplate.boundValueOps("sign:"+goodsId).set(null,expireTime, TimeUnit.SECONDS);
        }else {
            // 插入
            // userIds循环插入
            userIds.forEach(e -> {
                // 每次先移除 不论存在否 移出再插入
                redisTemplate.opsForList().remove(goodsId,1,e);
                redisTemplate.opsForList().rightPush(goodsId,e);
            });

        }
        return "登记成功";
    }

    @GetMapping("/test")
    public BailianSeckill test(){
        LambdaQueryWrapper<BailianSeckill> qw = new LambdaQueryWrapper<>();
        qw.eq(BailianSeckill::getGoodsId,"601196945408");
        return seckillService.getOne(qw);
    }

}
