package com.cuning.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * @author dengteng
 * @title: GoodsArriveProducer
 * @projectName cuning-bailian
 * @description: 商品到货消息生产者
 * @date 2022/6/13
 */
@Slf4j
@Component
public class GoodsArriveProducer {

    @Autowired(required = false)
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * @Param: []
     * @return: void
     * @Author: dengteng
     * @Date: 2022/5/28
     * @Description: 使用P2P模式，发送消息到队列
     */
    public void sendMessageToGoodsQueue(String queueName, String queueMsg){

        log.info("发送消息：{},到队列：{}",queueMsg,queueName);

        // 创建目标消息队列对象（消息的缓存容器，缓存发送的消息体）
        Destination destination = new ActiveMQQueue(queueName);

        // 发送消息到目标队列
        jmsMessagingTemplate.convertAndSend(destination,queueMsg);

    }

}
