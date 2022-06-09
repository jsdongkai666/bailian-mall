package com.cuning.bean.logistics;

import lombok.Data;

import java.util.List;

/**
 * @author dengteng
 * @title: LogisticsInfo
 * @projectName logisticsApi
 * @description: 物流信息
 * @date 2022/6/9
 */
@Data
public class LogisticsInfo {

    /**
     * 物流运单号
     */
    private String logisticCode;

    /**
     * 快递公司编码
     */
    private String shipperCode;

    /**
     * 痕迹信息
     */
    private List<Trace> traces;

    /**
     * 物流状态：2-在途中,3-签收,4-问题件
     */
    private String State;

    /**
     * 是否查询成功
     */
    private boolean success;

}
