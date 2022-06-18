package com.cuning.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created On : 2022/06/18.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 用户信息实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoVO {


    /**
     * 用户名
     */
    private String userName;


    /**
     * 用户性别
     */
    private String userSex;

    /**
     * 用户电话
     */
    private String userTel;

    /**
     * 用户生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date userBirth;

    /**
     * 用户邮箱
     */
    private String userMail;

    /**
     * 用户头像
     */
    private String userHeadImg;

    /**
     * 用户积分
     */
    private Integer userPoints;

    /**
     * 会员等级
     */
    private Integer vipLevel;

    /**
     * 会员到期时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date vipDate;

    /**
     * 上一次签到日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastCheckDate;

    /**
     * 当月签到次数
     */
    private Integer checkCounts;

    /**
     * 当天签到状态
     */
    private String checkStatus;
}
