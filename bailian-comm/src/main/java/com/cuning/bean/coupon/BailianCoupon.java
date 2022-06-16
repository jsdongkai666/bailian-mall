package com.cuning.bean.coupon;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.*;
import java.util.Date;

/**
 * @author dengteng
 * @title: BailianCoupon
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/14
 */
@Data
@ApiModel("优惠券实体")
public class BailianCoupon {

    @ApiModelProperty("id")
    @TableId
    private String id;

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("优惠券类型")
    private Integer couponType;

    @ApiModelProperty("优惠金额")
    private Double couponAmount;

    @ApiModelProperty("满减金额")
    private Double fullAmount;

    @ApiModelProperty("发放开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date grantStartTime;

    @ApiModelProperty("发放结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date grantEndTime;

    @ApiModelProperty("生效时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date effectiveStartTime;

    @ApiModelProperty("失效时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date effectiveEndTime;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("发放数量")
    private Integer quantity;

    @ApiModelProperty("每人可重复领取数量")
    private Integer repeatQuantity;

    @ApiModelProperty("商品分类id")
    private Integer categoryId;
}
