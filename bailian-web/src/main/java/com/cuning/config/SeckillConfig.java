package com.cuning.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author tengjiaozhai
 * @Description 秒杀抢购的配置类
 * @createTime 2022年06月07日 16:00:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "seckill.buy")
public class SeckillConfig {

    /**
     * 初始化商品库存数量
     */
    private Integer prodStockCount;

    private String prodStockPrefix;

    /**
     * 锁定抢购用户统一前缀
     */
    private String lockUserPrefix;

    private Integer lockUserTime;

    /**
     * 锁定抢购商品库存的统一前缀
     */
    private String prodStockLockPrefix;

    /**
     * 锁定抢购商品库存的时长
     */
    private Integer prodStockLockTime;
}
