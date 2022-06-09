package com.cuning.bean.shoppingcarts;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 购物车实体类
 */
@ApiModel(description="购物车")
public class shoppingCarts {
    @ApiModelProperty("用户id")
    private String userId;

}
