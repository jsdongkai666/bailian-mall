package com.cuning.bean.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月09日 15:03:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("bailian_user")
public class User {

    private String userId;

    private String userName;

    private String userPassword;

    private String userTel;

    private String userMail;

    private Integer userPoints;

    private Integer vipLevel;
}
