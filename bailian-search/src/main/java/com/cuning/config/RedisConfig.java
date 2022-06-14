package com.cuning.config;


import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * Created On : 2022/05/12.
 * <p>
 * Author     : lixu
 * <p>
 * Description: redis配置类，自定义redisTemplate，支持string的key，object的value
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 定义redis操作模板对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 设置连接工程
        template.setConnectionFactory(redisConnectionFactory);

        // 设置自定义序列化方式
        // key序列化方式，使用String的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // value序列化方式，使用fastjson提供的序列化方式，序列化Object类型对象
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);

        // 指定序列化和反序列化方式
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);

        // 初始化自定义模板
        template.afterPropertiesSet();

        return template;
    }
}
