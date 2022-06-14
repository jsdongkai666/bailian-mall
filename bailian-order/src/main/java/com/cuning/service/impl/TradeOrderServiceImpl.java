package com.cuning.service.impl;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.cuning.constant.WechatPayConstant;
import com.cuning.service.AlipayService;
import com.cuning.service.TradeOrderService;
import com.cuning.service.WechatPayService;
import com.cuning.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单业务实现类
 */
@Slf4j
@Service
public class TradeOrderServiceImpl implements TradeOrderService {

    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired(required = false)
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private WechatPayService wechatPayService;

    @Override
    public void saveTradeOrder(Map<String, String> tradeOrder) {
        // 创建异步生成订单的消息队列
        Destination destination = new ActiveMQQueue("seckill-buy-save-order-queue");

        if (redisUtils.set(tradeOrder.get("tradeOrderNo"),0)){
            jmsMessagingTemplate.convertAndSend(destination,tradeOrder);
        }
    }

    @Override
    public Map<String, String> findTradeOrder(String userId, String orderId) {
        //模拟返回订单数据
        Map<String, String> tradeOrder = new HashMap<>();
        tradeOrder.put("userId",userId);
        tradeOrder.put("orderId",orderId);
        tradeOrder.put("orderStatus",redisUtils.get(orderId).toString());
        return tradeOrder;
    }

    @Override
    public Map<String, String> payTradeOrder(String userId, String prodId, String orderId) throws Exception {
        //TODO 调用账务支付前，封装接口参数，数据都是来源于抢购订单，查询到支付数据，再请求支付
        //模拟获取到支付数据，再发起支付请求
        return this.wechatPayUnifiedorderOrder(orderId,1,prodId);
    }

    @Override
    public String aliPayTradeOrder(String userId, String prodId, String orderId) throws Exception {
        return this.pay(prodId,orderId,"1000");
    }


    private Map<String, String> wechatPayUnifiedorderOrder(String body, int totalFee, String productId) throws Exception {
        String unifiedOrderParamsXml = wechatPayService.wechatPayUnifieOrderParamsXml(body, totalFee, productId);


        log.info("----- 1 请求微信官方，进行统一下单，接口参数:{} ------", unifiedOrderParamsXml);

        // 定义统一返回结果集合
        Map<String, String> resultMap = new HashMap<>();
        // 请求微信官方统一下单接口，下预支付订单
        Map<String, String> unifiedOrderResultMap = wechatPayService.wechatPayUnifiedOrder(unifiedOrderParamsXml);

        // 解析统一下预支付订单的结果，获取支付的二维码链接，此链接用于生成支付二维码给用户支付
        if (WechatPayConstant.WECHAT_PAY_SUCCESS.equals(unifiedOrderResultMap.get("return_code"))
                && WechatPayConstant.WECHAT_PAY_SUCCESS.equals(unifiedOrderResultMap.get("result_code"))) {
            // 交易类型
            resultMap.put("trade_type", unifiedOrderResultMap.get("trade_type"));
            // 预支付交易会话标识
            resultMap.put("prepay_id", unifiedOrderResultMap.get("prepay_id"));
            // 二维码链接code_url
            resultMap.put("code_url", unifiedOrderResultMap.get("code_url"));

            return resultMap;
        }

        // 统一返回支付失败结果
        resultMap.put("errCode",unifiedOrderResultMap.get("return_code"));
        resultMap.put("errMsg",unifiedOrderResultMap.get("return_msg"));

        // 返回支付失败结果
        return resultMap;
    }

    private String pay( String subject, String orderNo,String totalfee) throws Exception {

        AlipayTradePagePayResponse response = Factory.Payment
                //选择网页支付平台
                .Page()
                //调用支付方法:订单名称、订单号、金额、回调页面
                .pay(subject, orderNo, totalfee, returnUrl);
        //这里可以加入业务代码：比如生成订单等等。。
        System.out.println(response.body);
        return response.body;
    }
}

