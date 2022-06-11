package com.cuning.bean.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月09日 15:03:00
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("bailian_user")
@ToString
public class User {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("用户电话")
    private String userTel;

    @ApiModelProperty("用户唯一标识")
    private String userOpenid;

    @ApiModelProperty("用户邮箱")
    private String userMail;

    @ApiModelProperty("用户性别")
    private String userSex;

    @ApiModelProperty("用户头像")
    private String userHeadImg;

    @ApiModelProperty("用户积分")
    private Integer userPoints;

    @ApiModelProperty("会员等级")
    private Integer vipLevel;
}
