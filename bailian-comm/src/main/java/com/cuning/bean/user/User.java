package com.cuning.bean.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月09日 15:03:00
 * 命名规则：50+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("bailian_user")
@ToString
public class User implements Serializable {

    @TableId
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("用户性别")
    private String userSex;

    @ApiModelProperty("用户电话")
    private String userTel;

    @ApiModelProperty("用户生日")
    private Date userBirth;

    @ApiModelProperty("用户唯一标识")
    private String userOpenid;

    @ApiModelProperty("用户邮箱")
    private String userMail;

    @ApiModelProperty("用户头像")
    private String userHeadImg;

    @ApiModelProperty("用户积分")
    private Integer userPoints;

    @ApiModelProperty("会员等级")
    private Integer vipLevel;

    @ApiModelProperty("会员到期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date vipDate;

    @ApiModelProperty("上一次签到日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastCheckDate;

    @ApiModelProperty("当月签到次数")
    private Integer checkCounts;

    @ApiModelProperty("当天签到状态")
    private Integer checkStatus;

}
