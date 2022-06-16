package com.cuning.controller;

import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.constant.CommonConstant;
import com.cuning.constant.GoodsConstant;
import com.cuning.producer.GoodsArriveProducer;
import com.cuning.service.GoodsInfoService;
import com.cuning.util.RedisUtils;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengteng
 * @title: GoodsArriveController
 * @projectName cuning-bailian
 * @description: 商品补货--到货提醒--用户设置到货提醒
 * @date 2022/6/13
 */
@Slf4j
@RestController
@Api(tags = "商品补货和到货提醒操作入口")
public class GoodsArriveController {
    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private GoodsArriveProducer goodsArriveProducer;

    /**
    * @Param: []
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/13
    * @Description: 用户设置到货提醒
    */
    @ApiOperation("用户设置到货提醒")
    @GetMapping("/setArrivalReminders")
    public Map<String, String> setArrivalReminders(@RequestParam("userId") String userId, @RequestParam("goodsId") String goodsId){
        Map<String, String> result = new HashMap<>();

        BailianGoodsInfo byId = goodsInfoService.getById(goodsId);
        if (byId == null) {
            result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            result.put("msg","商品不存在！！！");
            return  result;
        }

        redisUtils.incr(goodsId+":"+userId,1);
        redisUtils.expire(goodsId+":"+userId,60);
        if (Integer.valueOf(redisUtils.get(goodsId+":"+userId).toString()) > 4){
            result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            result.put("msg","操作过于频繁，请稍后再试！");
            return  result;
        }

       return goodsInfoService.setArrivalReminders(userId,goodsId);

    }

    /**
    * @Param: [java.lang.String, java.lang.Integer]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/13
    * @Description: 商品补货接口
    */
    @ApiOperation("商品补货接口")
    @GetMapping("/replenishment")
    public Map<String, String> replenishment(@RequestParam("goodsId") String goodsId, @RequestParam("stockNum") Integer stockNum){
        Map<String, String> result = new HashMap<>();

        BailianGoodsInfo byId = goodsInfoService.getById(goodsId);
        if (byId == null) {
            result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            result.put("msg","商品不存在！！！");
            return  result;
        }

        if (goodsInfoService.replenishment(goodsId, stockNum)) {
            goodsArriveProducer.sendMessageToGoodsQueue(GoodsConstant.GOODS_ARRIVE_REMIND_QUEUE_NAME,goodsId);
            result.put("code",CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            result.put("msg","补货成功！");
            return result;
        }
        result.put("code",CommonConstant.UNIFY_RETURN_FAIL_CODE);
        result.put("msg","补货失败！");
        return result;
    }

}
