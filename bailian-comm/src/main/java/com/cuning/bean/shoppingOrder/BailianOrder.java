package com.cuning.bean.shoppingOrder;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 购物车实体类
 */
@ApiModel(description="订单")
@Data
public class BailianOrder {
    @ApiModelProperty("订单主键")
    @TableId
    private Integer orderId;

    @ApiModelProperty("订单标号")
    private String orderNo;

    @ApiModelProperty("商品标号")
    private String goodsNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品价格")
    private Double orderPrice;

    @ApiModelProperty("商品获得积分")
    private Integer orderPoint;

    @ApiModelProperty("物流状态")
    private Integer logisticsStatus;

    @ApiModelProperty("买家名称")
    private String userName;

    @ApiModelProperty("买家账号")
    private Integer userNo;

    @ApiModelProperty("买家地址")
    private String userAddress;

    @ApiModelProperty("物流费用")
    private Double logisticsPrice;
}
