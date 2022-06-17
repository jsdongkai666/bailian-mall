package com.cuning.service;

import com.cuning.bean.logistics.LogisticsInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author dengteng
 * @title: LogisticsFeignService
 * @projectName cuning-bailian
 * @description: 物流管理远程feign远程调用
 * @date 2022/6/14
 */
@FeignClient(value = "bailian-logistics")
public interface LogisticsFeignService {

    /** 
    * @Param: [java.lang.String, java.lang.String] 
    * @return: com.cuning.bean.logistics.LogisticsInfo 
    * @Author: dengteng
    * @Date: 2022/6/17 
    * @Description: 查询物流信息 
    */
    @PostMapping("/getlogisticsInfo")
    LogisticsInfo getlogisticsInfoByTrackingNumber(@RequestParam("logisticCode") String logisticCode, @RequestParam("shipperCode") String shipperCode);

    /** 
    * @Param: [java.lang.String] 
    * @return: java.util.Map<java.lang.String,java.lang.String> 
    * @Author: dengteng
    * @Date: 2022/6/17 
    * @Description: 发货 
    */
    @GetMapping("/ship")
    Map<String, String> ship(@RequestParam("orderId")String orderNo);
    
}
