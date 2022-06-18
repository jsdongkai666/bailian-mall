package com.cuning.config;

import com.cuning.listener.RedisEventMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月16日 15:55:00
 */
@Configuration
public class RedisConfiguration {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 监听key过期事件
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    /**
     * Redis Key失效监听器注册为Bean.
     *
     * @param redisMessageListenerContainer the redis message listener container
     * @return the redis event message listener
     */
    @Bean
    public RedisEventMessageListener redisEventMessageListener(RedisMessageListenerContainer redisMessageListenerContainer){
        return new RedisEventMessageListener(redisMessageListenerContainer);
    }

    /**
     * 指定监听库
     */
//    @Bean
//    public ChannelTopic expiredTopic() {
//        return new ChannelTopic("__keyevent@1__:expired");
//    }

}
