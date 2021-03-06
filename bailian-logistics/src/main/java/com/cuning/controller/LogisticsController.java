package com.cuning.controller;

import com.cuning.bean.logistics.LogisticsCode;
import com.cuning.bean.logistics.LogisticsInfo;
import com.cuning.constant.CommonConstant;
import com.cuning.service.KdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    * @Param: [java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/17
    * @Description: 商品发货
    */
    @GetMapping("/ship")
    public Map<String, String> ship(@RequestParam("orderId")String orderNo){
        Map<String, String> result = new HashMap<>();

        try {
            Map<String, String> map = kdService.queryOrderStatus(orderNo);
            if (!map.get("code").equals(CommonConstant.UNIFY_RETURN_FAIL_CODE)) {
               return map;
            }
        } catch (Exception e) {
            result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            result.put("msg", "订单不存在");
            return result;
        }

        // 发货
        LogisticsCode logisticsCode = kdService.orderShip(orderNo);
        if (logisticsCode != null) {
            result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            result.put("msg", "商品发货成功");
            result.put("logisticCode", logisticsCode.getLogisticCode());
            result.put("shipperCode", logisticsCode.getShipperCode());
            return result;
        }

        result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
        result.put("msg", "商品发货失败！");
        return result;
    }


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
