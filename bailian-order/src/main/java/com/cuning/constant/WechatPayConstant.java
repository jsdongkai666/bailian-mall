package com.cuning.constant;

/**
 * @author dengteng
 * @title: WechatPayConstant
 * @projectName springcloud-93
 * @description: 微信支付系统常量类
 * @date 2022/5/31
 */
public class WechatPayConstant {

    // 系统内部订单号标识
    public static final String WECHAT_PAY_TRADE_ORDER_NO_PREFIX = "T";

    // 系统日期格式化字符串,到秒
    public static final String WECHAT_PAY_TIME_PATTERN_ALL = "yyyyMMddHHmmSS";

    // 系统日期格式化字符串,到天
    public static final String WECHAT_PAY_TIME_PATTERN_DAY = "yyyyMMdd";

    // 微信支付类型
    public static final String WECHAT_PAY_TRADE_TYPE_NATIVE = "NATIVE";

    // 签名加密方式
    public static final String WECHAT_PAY_SING_TYPE_MD5 = "MD5";
    public static final String WECHAT_PAY_SING_TYPE_HMACSHA256 = "HMAC-SHA256";

    // 签名的参数名-sign
    public static final String WECHAT_PAY_FIELD_SIGN = "sign";
    public static final String WECHAT_PAY_FIELD_SIGN_TYPE = "sign_type";

    // 系统字符集编码
    public static final String WECHAT_PAY_ENCODEING_UTF8 = "utf8";

    // 接口返回结果
    public static final String WECHAT_PAY_FAIL     = "FAIL";
    public static final String WECHAT_PAY_SUCCESS  = "SUCCESS";


    // 微信异步回调，同步返回成功响应结果
    public static final String WECHT_PAY_NOTIFY_RESULT_RESPONSE_SUCCES= "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    public static final String WECHT_PAY_NOTIFY_RESULT_RESPONSE_FAIL= "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[NO]]></return_msg></xml>";



}
