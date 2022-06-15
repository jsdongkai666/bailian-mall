package com.cuning.bean.shoppingcarts;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 购物车实体类
 */
@ApiModel(description="购物车")
@Data
public class BailianCarts {
    private String id;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("用户id")
    private String userId;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;

    @TableField(exist = false)
    private List<BailianCartProducts> productsList;

}
