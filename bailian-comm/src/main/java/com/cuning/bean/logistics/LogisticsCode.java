package com.cuning.bean.logistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dengteng
 * @title: LogisticCode
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsCode {

    /**
     * 物流运单号
     */
    private String logisticCode;

    /**
     * 快递公司编码
     */
    private String shipperCode;

    /**
     * 订单编号
     */
    private String orderNo;

}
