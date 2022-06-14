package com.cuning.service.impl;

import com.cuning.config.SeckillConfig;
import com.cuning.service.SeckillService;
import com.cuning.service.SeckillTradeOrderFeignService;
import com.cuning.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月07日 15:55:00
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SeckillConfig seckillConfig;

    @Autowired
    private SeckillTradeOrderFeignService seckillTradeOrderFeignService;

    @Override
    public boolean limitByUserCount(String prodId) {
        // 如果请求数限制在1000以内,比如库存数量的2倍
        return redisUtils.incr(prodId, 1) > seckillConfig.getProdStockCount() * 2;
    }

    @Override
    public void initProdStock2Redis(String prodId) {
        // 判断redis中，当前商品已经存在库存数据，如果存在，不需要再次通过，如果不存在，才需要同步
        if (ObjectUtils.isEmpty(redisUtils.get(seckillConfig.getProdStockPrefix() + ":" + prodId))) {
            redisUtils.set(seckillConfig.getProdStockPrefix() + ":" + prodId, seckillConfig.getProdStockCount());
            log.info("**** 初始化商品：{}，库存：{}，到redis缓存 ****", prodId, seckillConfig.getProdStockPrefix());
        }
    }

    @Override
    public boolean checkProdStockEnough(String prodId, Integer buyCount) {
        // redis获取商品库存，跟购买数量进行比较，必须大于等于

        return Integer.valueOf(redisUtils.get(seckillConfig.getProdStockPrefix() + ":" + prodId).toString()) >= buyCount;
    }

    @Override
    public boolean checkSeckillUserBought(String userId, String prodId) {
        // 借助redis的分布式锁： setnx,根据用户编号和商品编号，进行锁定
        return !redisUtils.lock(seckillConfig.getLockUserPrefix() + ":" + userId + ":" + prodId, null, seckillConfig.getLockUserTime());
    }

    @Override
    public boolean checkProdStockLocked(String prodId) {
        // 借助redis分布式锁，setnx，直接尝试库存上锁，如果成功吗，redis中库存还没有锁定，可以购买，如果失败，酷讯已经被锁定，不可以购买
        return !redisUtils.lock(seckillConfig.getProdStockLockPrefix() + ":" + prodId, null, seckillConfig.getProdStockLockTime());
    }

    @Override
    public boolean subProdStock(String prodId, Integer buyCount) {
        // 扣减缓存中商品库存，同时可以发送异步消息队列，更新数据库库存，保证数据一致
        return redisUtils.decr(seckillConfig.getProdStockPrefix() + ":" + prodId, buyCount) < 0;
    }

    @Override
    public void unlockProdStock(String prodId) {
        // 释放库存锁
        redisUtils.delLock(seckillConfig.getProdStockLockPrefix() + ":" + prodId);
    }

    //生成抢购订单
    @Override
    public String createOrder(String userId, String prodId, Integer buyCount) {
        //使用远程调用订单中心接口，返回订单编号（实际应该是一个订单实体）

        String tradeOrderNo = seckillTradeOrderFeignService.invokeCreateTradeOrderFeignService(userId, prodId, buyCount);
        return tradeOrderNo;
    }

    @Override
    public Map<String, String> getOrderNoInfo(String userId,String tradeOrderNo) {
        //远程调用订单中心接口查询订单详情，使用service
        return seckillTradeOrderFeignService.invokeQueryTradeOrderFeign(userId,tradeOrderNo);
    }

    @Override
    public Map<String, String> payOrder(String userId, String prodId, String tradeOrderNo) {
        //远程调用订单中心接口查询订单详情，使用service
        return seckillTradeOrderFeignService.invokePayTradeOrderFeign(userId,prodId,tradeOrderNo);
    }

    @Override
    public String AliPayOrder(String userId, String prodId, String tradeOrderNo) {
        return seckillTradeOrderFeignService.invokeAliPayTradeOrderFeign(userId,prodId,tradeOrderNo);
    }

//    查询订单状态 1为true 0为false

}
