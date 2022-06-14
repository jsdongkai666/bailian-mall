package com.cuning.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.bean.user.User;
import com.cuning.constant.GoodsConstant;
import com.cuning.service.GoodsInfoService;
import com.cuning.util.RedisUtils;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dengteng
 * @title: GoodsArriveConstant
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/13
 */
@Slf4j
@Component
public class GoodsArriveListener {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private JavaMailSenderImpl mailSender;



    /**
     * @Param: [java.lang.String]
     * @return: void
     * @Author: dengteng
     * @Date: 2022/5/28
     * @Description: 从指定队列中，消费消息，进行业务处理
     */
    // 队列的名字
    @JmsListener(destination = GoodsConstant.GOODS_ARRIVE_REMIND_QUEUE_NAME)
    public void consumerMsgFromQueueUseP2P(String queueMsg){
        log.info("消费者one，消费消息:{}",queueMsg);

        List<Object> userIdList = redisUtils.lGet(queueMsg + ":reminder", 0, -1);

        for (Object useridObject: userIdList) {
            String userId = useridObject.toString();

            // 模拟发送消息
            log.info("用户：{}，收到商品：{}的到货提醒",userId,queueMsg);
            sendmail(queueMsg,"1964270150@qq.com");

            // 从redis队列中删除--取消到货提醒
            goodsInfoService.cancelArrivalReminders(userId,queueMsg);

        }

    }

    public void sendmail(String goodsId, String mail){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();


        QueryWrapper<BailianGoodsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BailianGoodsInfo::getGoodsId,goodsId);
        BailianGoodsInfo one = goodsInfoService.getOne(queryWrapper);

        simpleMailMessage.setSubject("到货提醒");
        simpleMailMessage.setText(one.getGoodsName() + "已经到货了！快来抢购吧");

        //发送给谁
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setFrom("dengteng1211@163.com");


        mailSender.send(simpleMailMessage);
    }


}
