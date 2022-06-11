package com.cuning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.user.User;
import com.cuning.vo.UserVO;

import java.util.Map;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月09日 15:11:00
 */
public interface UserService extends IService<User> {


    User executeRegister(String userName, String userPassword);

    Map<String,Object> executeLogin(UserVO user);

    Map<String, Object> executeTelLogin(String tel, String captcha);

    boolean modUserInfo(User user);

    Map<String,String> modPassword(User user,String password,String newPassword,String newPasswordAgain);

}
