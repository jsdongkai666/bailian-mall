package com.cuning.bean.shoppingOrder;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;


/**
 * Created On : 2022/6/11.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: 订单实体类
 */
@ApiModel(description="订单")
@Data
public class BailianOrder {
    @ApiModelProperty("订单主键")
    @TableId
    private Integer orderId;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("用户主键")
    private String userId;

    @ApiModelProperty("订单总价")
    private Integer totalPrice;

    @ApiModelProperty("支付状态")
    private Integer payStatus;

    @ApiModelProperty("支付类型")
    private Integer payType;

    @ApiModelProperty("支付时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date payTime;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("订单body")
    private String extraInfo;

    @ApiModelProperty("收货人姓名")
    private String userName;

    @ApiModelProperty("收货人手机号")
    private String userPhone;

    @ApiModelProperty("收货人收货地址")
    private String userAddress;

    @ApiModelProperty("删除标识字段")
    private Integer isDeleted;

    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty("最新修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    private List<BailianOrderItem> bailianOrders;
}
