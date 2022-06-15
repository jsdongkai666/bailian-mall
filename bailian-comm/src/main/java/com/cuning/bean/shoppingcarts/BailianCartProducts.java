package com.cuning.bean.shoppingcarts;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/***
 * Created On : 2022/6/15.
 * <p>
 * Author     : kk
 * <p>
 * Description: 命名规则： 43+
 */
@ApiModel(description="购物车")
@Data
public class BailianCartProducts {

    private String id;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("购物车id")
    private String cartId;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("主商品id")
    private String productId;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("购买数量")
    private Integer buyNum;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;
}
