package com.cuning.listener;

import com.cuning.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class TradeOrderSaveListener {

    @Autowired
    private RedisUtils redisUtils;

    @JmsListener(destination = "seckill-buy-save-order-queue")
    public void saveTradeOrderFromQueue(Map<String,String> tradeOrder){
        log.info("+++ 生成订单请求{}：开始入库 +++",tradeOrder);

        // TODO 直接将我们队列中获取的订单实体入库,同时改变redis中订单状态为1

        // 模拟订单状态的改变
        redisUtils.set(tradeOrder.get("tradeOrderNo"),1);
        log.info("+++ 生成订单请求{}：开始入库 +++",tradeOrder);
    }
}