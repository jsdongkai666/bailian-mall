package com.cuning.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dengteng
 * @title: WechatPayConfig
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/10
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat.pay")
public class WechatPayConfig {

    /**
     * 应用id
     */
    private String appId;
    /**
     * 商户id
     */
    private String mchId;
    /**
     * 商户秘钥
     */
    private String mchkey;
    /**
     * 统一下单接口地址
     */
    private String orderUri;

    /**
     * 支付结果异步回调地址
     */
    private String notifyUri;

    /**
     * 查询订单地址
     */
    private String orderqueryUri;

}
