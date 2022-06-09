package com.cuning.bean.shoppingcarts;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 购物车实体类
 */
@ApiModel(description="购物车")
@Data
public class BailianCarts {
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("店铺id")
    private String shopId;

    @ApiModelProperty("购物车id")
    private String cartId;

    @ApiModelProperty("主商品id")
    private String productId;

    @ApiModelProperty("购买数量")
    private Integer buyNum;

    @ApiModelProperty("创建时间")
    private Date createdAt;

    @ApiModelProperty("更新时间")
    private Date updatedAt;

}
