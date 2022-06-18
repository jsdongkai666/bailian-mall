package com.cuning.service.impl;


import com.cuning.constant.PaymentConstant;
import com.cuning.constant.WechatPayConstant;
import com.cuning.producer.AccountPayResultProducer;
import com.cuning.service.WechatPayService;
import com.cuning.util.HttpClient4Util;
import com.cuning.util.WechatPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuning.config.WechatPayConfig;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author dengteng
 * @title: WechatPayServiceImpl
 * @projectName springcloud-93
 * @description: 微信支付业务接口实现类
 * @date 2022/5/31
 */
@Service
public class WechatPayServiceImpl implements WechatPayService {

    @Autowired
    private WechatPayConfig wechatPayConfig;

    @Autowired
    private AccountPayResultProducer accountPayResultProducer;


    @Override
    public String wechatPayUnifieOrderParamsXml(String body, int totalFee, String productId) throws Exception {

        // 微信官方接口集合参数要求：非空参数值的参数按照参数名ASCII码从小到大排序（字典序），参数名区分大小写，sign参数不参与签名

        // 定义微信官方统一下单参数集合，要求根据参数名字典序排序，可选择集合TreeMap，自动根据key进行自然排序
        Map<String, String> paramsMap = new TreeMap<>();

        // appId
        paramsMap.put("appid", wechatPayConfig.getAppId());
        // 商户号
        paramsMap.put("mch_id", wechatPayConfig.getMchId());
        // 设备号 PC网页或公众号内支付可以传"WEB"
        paramsMap.put("device_info", "WEB");

        // 随机字符串，长度要求在32位以内，增加签名安全性
        paramsMap.put("nonce_str", WechatPayUtil.generateNonceStrUseRandom());

        // 商品描述
        paramsMap.put("body", body);

        // 附加数据
        paramsMap.put("attach", "kgckh93");

        // 商户订单号
        //paramsMap.put("out_trade_no", WechatPayUtil.generateTradeOrderNo());
        paramsMap.put("out_trade_no", body);

        // 标价金额,单位是fen
        paramsMap.put("total_fee", String.valueOf(totalFee));
        // 终端ip
        paramsMap.put("spbill_create_ip", "127.0.0.1");

        // 通知地址,异步接受微信支付结果通知的毁掉地址，通知url必须位外网可访问的url
        paramsMap.put("notify_url", wechatPayConfig.getNotifyUri());

        // 交易类型 NATIVE pc端
        paramsMap.put("trade_type", WechatPayConstant.WECHAT_PAY_TRADE_TYPE_NATIVE);

        // 商品id
        paramsMap.put("product_id", productId);

        // 生成签名，sign不参与签名，作为接口参数传递签名的结果
        // 一般情况，都是将所有的签名参数确认后，最后进行签名处理，将签名后的sign放入参数接口，返回
        paramsMap.put("sign", WechatPayUtil.generateSignature(paramsMap, wechatPayConfig.getMchkey()));


        return WechatPayUtil.generateParamStrMapToXml(paramsMap);
    }

    @Override
    public Map<String, String> wechatPayUnifiedOrder(String unifiedOrderParamsXml) throws Exception {

        // 内部发起http请求到微信官方进行统一下预支付订单，必须使用post请求，获取同步返回结果，结果字符串xml格式化
        String response4PostByString = HttpClient4Util.getResponse4PostByString(wechatPayConfig.getOrderUri(), unifiedOrderParamsXml, WechatPayConstant.WECHAT_PAY_ENCODEING_UTF8);

        Map<String, String> map = WechatPayUtil.generateResultMapXmlToMap(response4PostByString);

        return map;
    }

    @Override
    public String wxchatPayNotifyResultResolve(String wxpayNotifyResltXxml) throws Exception {

        // 将微信官方异步回调结果，转换为map集合
        Map<String, String> wxpayNotifyResltMap = WechatPayUtil.generateResultMapXmlToMap(wxpayNotifyResltXxml);

        // 解析成功结果
        if (WechatPayConstant.WECHAT_PAY_SUCCESS.equals(wxpayNotifyResltMap.get("return_code"))) {
            // 商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄露导致出现“假通知”，造成资金损失。
            if (WechatPayUtil.isSignatureValid(wxpayNotifyResltMap, wechatPayConfig.getMchkey())) {

                // TODO 业务处理
                // 发送支付成功地响应结果，给订单中心该状态，此处应该发的是topic消息，方便测试，使用队列
                wxpayNotifyResltMap.put("order_id",wxpayNotifyResltMap.get("out_trade_no"));
                accountPayResultProducer.payOrderResultNotify(PaymentConstant.PAYMENT_MESSAGE_QUEUE_NAME,wxpayNotifyResltMap);

                return WechatPayConstant.WECHT_PAY_NOTIFY_RESULT_RESPONSE_SUCCES;
            }


        }

        return WechatPayConstant.WECHT_PAY_NOTIFY_RESULT_RESPONSE_FAIL;
    }

    @Override
    public Map<String, String> wxChatOrderQuery(String outTradeNo) throws Exception {

        Map<String, String> paramsMap = new TreeMap<>();
        paramsMap.put("appid", wechatPayConfig.getAppId());
        paramsMap.put("mch_id", wechatPayConfig.getMchId());
        paramsMap.put("out_trade_no", outTradeNo);
        paramsMap.put("nonce_str", WechatPayUtil.generateNonceStrUseRandom());
        paramsMap.put("sign_type", WechatPayConstant.WECHAT_PAY_SING_TYPE_MD5);

        paramsMap.put("sign", WechatPayUtil.generateSignature(paramsMap, wechatPayConfig.getMchkey()));

        String generateParamStrMapToXml = WechatPayUtil.generateParamStrMapToXml(paramsMap);

        String response4PostByString = HttpClient4Util.getResponse4PostByString(wechatPayConfig.getOrderqueryUri(), generateParamStrMapToXml, "utf-8");

        return WechatPayUtil.generateResultMapXmlToMap(response4PostByString);
    }
}
