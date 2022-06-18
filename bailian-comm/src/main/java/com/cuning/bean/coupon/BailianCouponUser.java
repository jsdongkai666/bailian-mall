package com.cuning.bean.coupon;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created On : 2022/5/13.
 * <p>
 * Author : zhukang
 * <p>
 * Description: 统一返回结果实体类
 */
@Data
@ApiModel("用户优惠券实体")
public class BailianCouponUser {

    @TableId
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("优惠券id")
    private String couponTempId;

    @ApiModelProperty("优惠类型")
    private Integer couponType;

    @ApiModelProperty("优惠券编码")
    private String codeNo;

    @ApiModelProperty("优惠金额")
    private Double couponAmount;

    @ApiModelProperty("满减金额")
    private Double fullAmount;

    @ApiModelProperty("生效时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date effectiveStartTime;

    @ApiModelProperty("失效时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date effectiveEndTime;

    @ApiModelProperty("状态")
    private String  status;

    @TableField(exist = false)
    @ApiModelProperty("状态信息")
    private String statusMsg;

    @ApiModelProperty("使用时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date useTime;

}
