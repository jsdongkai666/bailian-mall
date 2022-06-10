package com.cuning.service;


import com.cuning.bean.logistics.LogisticsCode;
import com.cuning.bean.logistics.LogisticsInfo;

/**
 * @author dengteng
 * @title: KdService
 * @projectName logisticsApi
 * @description: TODO
 * @date 2022/6/9
 */
public interface KdService {

    /**
     * @Param: [java.lang.String, java.lang.String]
     * @return: void
     * @Author: dengteng
     * @Date: 2022/6/9
     * @Description: 根据快递公司code和快递公司单号获取物流信息
     */
    LogisticsInfo queryLogisticsInfo(String ShipperCode, String LogisticCode) throws Exception;

    /**
     * @Param: [java.lang.String]
     * @return: com.cuning.bean.logistics.LogisticsCode
     * @Author: dengteng
     * @Date: 2022/6/9
     * @Description: 从常量类中存储的快递单号随机获取一个快递单号
     */
    LogisticsCode getCodeFromConstantList(String orderNo);

}
