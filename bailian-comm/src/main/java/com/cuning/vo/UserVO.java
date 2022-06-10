package com.cuning.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tengjiaozhai
 * @Description 用户登录实体类
 * @createTime 2022年06月09日 17:24:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {

    private String userName;

    private String userPassword;

    private String code;

}
