package com.cuning.controller;

import com.cuning.bean.logistics.LogisticsCode;
import com.cuning.bean.logistics.LogisticsInfo;
import com.cuning.service.KdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author dengteng
 * @title: LogisticsController
 * @projectName cuning-bailian
 * @description: 物流信息入口
 * @date 2022/6/9
 */
@RestController
@Slf4j
public class LogisticsController {

    @Autowired
    private KdService kdService;

    /**
     * @Param: [java.lang.String, java.lang.String]
     * @return: com.cuning.bean.logistics.LogisticsInfo
     * @Author: dengteng
     * @Date: 2022/6/9
     * @Description: 通过快递单号和快递公司编码获取快递物流信息
     */
    @PostMapping("/getlogisticsInfo")
    public LogisticsInfo getlogisticsInfoByTrackingNumber(@RequestParam("logisticCode") String logisticCode, @RequestParam("shipperCode") String shipperCode) {
        LogisticsInfo logisticsInfo;
        try {
            logisticsInfo = kdService.queryLogisticsInfo(shipperCode, logisticCode);
        } catch (Exception e) {
            log.error(e.getMessage());
            return LogisticsInfo.builder().success(false).build();
        }
        return logisticsInfo;
    }

    /**
     * @Param: [java.lang.String]
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @Author: dengteng
     * @Date: 2022/6/9
     * @Description: 获取快递单号和快递公司编号
     */
    @GetMapping("/getTrackingNumber")
    public LogisticsCode getTrackingNumber(@RequestParam("orderNo") String orderNo) {
        return kdService.getCodeFromConstantList(orderNo);
    }


}
