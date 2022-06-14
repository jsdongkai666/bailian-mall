package com.cuning.listener;

import com.cuning.constant.PaymentConstant;
import com.cuning.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author dengteng
 * @title: TradeOrderSaveListener
 * @projectName springcloud-93
 * @description: 生成订单消息队列监听
 * @date 2022/6/8
 */
@Slf4j
@Component
public class TradeOrderPayListener {
    @Autowired
    private RedisUtils redisUtils;

    @JmsListener(destination = PaymentConstant.PAYMENT_MESSAGE_QUEUE_NAME)
    public void saveTradeorderFromQueue(Map<String, String> resultMap){
        log.info("+++++ 订单支付结果：{},开始更新订单状态 ++++++",resultMap);

        // TODO 直接将队列中获取的订单实体，入库，同时改变redis的订单状态为1，已创建

        // 模拟订单状态的改变，将redis中订单状态给为2，已支付
        //redisUtils.set(resultMap.get("out_trade_no"),2);

        log.info("+++++ 收到订单支付结果,更新订单状态成功 ++++++");
    }

}
