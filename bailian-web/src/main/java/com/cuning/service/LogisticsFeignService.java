package com.cuning.service;

import com.cuning.bean.logistics.LogisticsInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author dengteng
 * @title: LogisticsFeignService
 * @projectName cuning-bailian
 * @description: 物流管理远程feign远程调用
 * @date 2022/6/14
 */
@FeignClient(value = "bailian-logistics")
public interface LogisticsFeignService {

    @PostMapping("/getlogisticsInfo")
    public LogisticsInfo getlogisticsInfoByTrackingNumber(@RequestParam("logisticCode") String logisticCode, @RequestParam("shipperCode") String shipperCode);

}
