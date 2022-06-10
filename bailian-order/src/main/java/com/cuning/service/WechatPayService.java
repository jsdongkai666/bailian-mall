package com.cuning.service;

import java.util.Map;

/**
 * @author dengteng
 * @title: WechatPayService
 * @projectName springcloud-93
 * @description: TODO
 * @date 2022/5/31
 */
public interface WechatPayService {

    /**
     * @Param: []
     * @return: java.lang.String
     * @Author: dengteng
     * @Date: 2022/5/31
     * @Description: 生成请求微信统一下预支付订单的接口完整参数-xml格式字符串返回
     */
    String wechatPayUnifieOrderParamsXml(String body, int totalFree, String productId) throws Exception;

    /**
    * @Param: [java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/5/31
    * @Description: 请求微信官方预支付接口
    */
    Map<String,String> wechatPayUnifiedOrder(String unifiedOrderParamsXml) throws Exception;

    /**
    * @Param: [java.lang.String]
    * @return: java.lang.String
    * @Author: dengteng
    * @Date: 2022/5/31
    * @Description: 解析微信官方异步回调支付结果，并返回同步响应字符串
    */
    String wxchatPayNotifyResultResolve(String wxpayNotifyResltXxml) throws Exception;

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/5/31
    * @Description: 通过商户订单号查询订单
    */
    Map<String,String >wxChatOrderQuery(String outTradeNo) throws Exception;

}
