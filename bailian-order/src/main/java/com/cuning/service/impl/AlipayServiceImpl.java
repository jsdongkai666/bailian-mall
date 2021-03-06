package com.cuning.service.impl;

import com.cuning.constant.PaymentConstant;
import com.cuning.producer.AccountPayResultProducer;
import com.cuning.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengteng
 * @title: AlipayServiceImpl
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/10
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AccountPayResultProducer accountPayResultProducer;

    @Override
    public void sendMsgToQueue(String orderId) {

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("order_id",orderId);
        resultMap.put("pay_type","Alipay");
        resultMap.put("success","true");
        accountPayResultProducer.payOrderResultNotify(PaymentConstant.PAYMENT_MESSAGE_QUEUE_NAME,resultMap);
    }
}
