package com.cuning.listener;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月16日 16:16:00
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cuning.bean.seckill.BailianSeckill;
import com.cuning.bean.user.User;
import com.cuning.service.SeckillService;
import com.cuning.service.UserService;
import com.cuning.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  当redis 中的key过期时，触发一个事件。
 *  我们可以算好需要执行的时间间隔作为key失效时间，这样就可以保证到点执行逻辑了。
 */
@Component
@Slf4j
public class RedisEventMessageListener extends KeyExpirationEventMessageListener {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private UserService userService;

    @Value("${spring.mail.userName}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;
    /**
     * Instantiates a new Redis event message listener.
     *
     * @param listenerContainer the listener container
     */
    public RedisEventMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    protected void doHandleMessage(Message message) {
        String key = message.toString();
        // 这个就是过期的key ，过期后，也就是事件触发后对应的value是拿不到的。
        // 这里实现业务逻辑，如果是服务器集群的话需要使用分布式锁进行抢占执行。
        try {
            String msg = new String(message.getBody(), "UTF-8");
            String channel = new String(message.getChannel(), "UTF-8");
            log.info("Redis-Listener Channel:"+channel+" Listen to the key:"+msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("key = " + key);
        log.info("end = " + LocalDateTime.now());
        // key存放时间 sign:key
        // 原型 减去sign:
        String originKey = key.replace("sign:", "");
        // 获取对应数据
        // key - originKey value - ids
        List<Object> ids = redisUtils.lGet(originKey, 0, -1);

        // 获取商品信息
        LambdaQueryWrapper<BailianSeckill> qw1 = new LambdaQueryWrapper<>();
        qw1.eq(BailianSeckill::getGoodsId,originKey);
        BailianSeckill seckill = seckillService.getOne(qw1);

        // 循环发送邮件
        ids.stream().forEach(e -> {
            String id = e.toString().replace("[","")
                    .replace("]","")
                    .replace("\"","");
            // 获取每个用户
            LambdaQueryWrapper<User> qw2 = new LambdaQueryWrapper<>();
            qw2.eq(User::getUserId,id);
            User user = userService.getOne(qw2);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(seckill.getGoodsName()+"的抢购活动马上开始了");
            mailMessage.setText(seckill.getGoodsName()+"抢购活动马上开始了");
            mailMessage.setTo(user.getUserMail());
            mailMessage.setFrom(from);
            javaMailSender.send(mailMessage);
            log.info("通知：{}成功",user.getUserName());
        });
        // 释放 redis key
        redisUtils.del(originKey);
    }
}
