package com.cuning.bean.shoppingOrder;

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

    @ApiModelProperty("订单关联购物项主键id")
    private Integer orderItemId;

    @ApiModelProperty("订单主键id")
    private Integer orderId;

    @ApiModelProperty("关联商品id")
    private Integer goodsId;

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

    private Integer commentaryType;
}
