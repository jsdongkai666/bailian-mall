package com.cuning.bean.shoppingOrder;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created On : 2022/6/11.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: 订单详细实体类
 */
@ApiModel(description="订单详细")
@Data
public class BailianOrderItem {

    @TableId
    @ApiModelProperty("订单关联购物项主键id")
    private String orderItemId;

    @ApiModelProperty("订单主键id")
    private String orderId;

    @ApiModelProperty("关联商品id")
    private String goodsId;

    @ApiModelProperty("下单时商品的名称")
    private String goodsName;

    @ApiModelProperty("下单时商品的主图")
    private String goodsCoverImg;

    @ApiModelProperty("下单时商品的价格")
    private Integer sellingPrice;

    @ApiModelProperty("数量")
    private Integer goodsCount;

    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty("商品评价情况")
    private Integer commentaryType;

    @ApiModelProperty("优惠券id")
    private String couponId;

    @ApiModelProperty("总价")
    private Double totalPrice;

    @ApiModelProperty("使用优惠券后总价")
    private Double priceAfterDiscount;

}
