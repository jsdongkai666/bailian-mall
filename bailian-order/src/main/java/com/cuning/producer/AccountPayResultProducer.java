package com.cuning.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.util.Map;

/**
 * @author dengteng
 * @title: AccountPayResultProducer
 * @projectName springcloud-93
 * @description: 队列模式，消息生产者
 * @date 2022/6/8
 */
@Slf4j
@Component
public class AccountPayResultProducer {

    @Autowired(required = false)
    private JmsMessagingTemplate jmsMessagingTemplate;


    /**
    * @Param: [java.lang.String, java.util.Map<java.lang.String,java.lang.String>]
    * @return: void
    * @Author: dengteng
    * @Date: 2022/6/8
    * @Description: 通知支付结果
    */
    public void payOrderResultNotify(String queueName, Map<String,String> resultMap){
        log.info("~~~~~~ 支付成功，发送结果到响应队列，结果通知到目标队列:{}，支付结果:{}",queueName,resultMap);

        // 创建目标消息队列对象
        Destination destination = new ActiveMQQueue(queueName);

        // 发送消息
        jmsMessagingTemplate.convertAndSend(destination,resultMap);
    }

}
