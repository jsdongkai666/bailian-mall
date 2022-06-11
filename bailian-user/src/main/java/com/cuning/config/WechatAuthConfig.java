package com.cuning.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年05月25日 14:50:00
 */
@Data
@ConfigurationProperties(prefix = "wechat.auth")
@Component
public class WechatAuthConfig {

    private String appId;

    private String appSecret;

    private String codeUri;

    private String redirectUri;

    private String accessTokenUri;

    private String userInfoUri;

    private String accessTokenRefresh;

    private String accessTokenCheck;
}
