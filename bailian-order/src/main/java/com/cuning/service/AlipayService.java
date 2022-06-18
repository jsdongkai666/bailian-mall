package com.cuning.service;

/**
 * @author dengteng
 * @title: AlipayService
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/10
 */
public interface AlipayService {

    void sendMsgToQueue(String orderId);

}
