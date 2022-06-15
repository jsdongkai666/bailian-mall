package com.cuning.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author dengteng
 * @title: UserInfoFeignService
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/14
 */
@FeignClient(value = "bailian-user")
public interface UserInfoFeignService {

    /**
    * @Param: [java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/14
    * @Description: 用户签到
    */
    @GetMapping("/checkIn")
    Map<String, String> checkIn(@RequestParam("userId") String userId);


    /**
    * @Param: [java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: dengteng
    * @Date: 2022/6/14
    * @Description: 用户当月签到记录
    */
    @GetMapping("/getUserCheckList")
    Map<String, Object> getUserCheckList(@RequestParam("userId")String userId);



}
