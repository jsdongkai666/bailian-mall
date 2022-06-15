package com.cuning.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author dengteng
 * @title: GoodsFeignService
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/14
 */
@FeignClient(value = "bailian-goods")
public interface GoodsFeignService {

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/14
    * @Description: 用户设置商品到货提醒
    */
    @GetMapping("/setArrivalReminders")
    Map<String, String> setArrivalReminders(@RequestParam("userId") String userId, @RequestParam("goodsId") String goodsId);

    /**
    * @Param: [java.lang.String, java.lang.Integer]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/14
    * @Description: 商品补货
    */
    @GetMapping("/replenishment")
    Map<String, String> replenishment(@RequestParam("goodsId") String goodsId, @RequestParam("stockNum") Integer stockNum);

}
