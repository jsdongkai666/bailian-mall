package com.cuning.bean.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dengteng
 * @title: LogisticCode
 * @projectName cuning-bailian
 * @description: 查询物流信息类
 * @date 2022/6/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("查询物流信息类")
public class LogisticsCode {

    /**
     * 物流运单号
     */
    @ApiModelProperty("物流运单号")
    private String logisticCode;

    /**
     * 快递公司编码
     */
    @ApiModelProperty("快递公司编码")
    private String shipperCode;

    /**
     * 订单编号
     */
    @ApiModelProperty("订单编号")
    private String orderNo;

}
