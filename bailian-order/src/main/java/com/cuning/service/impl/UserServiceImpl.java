package com.cuning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.user.User;
import com.cuning.mapper.UserMapper;
import com.cuning.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月16日 21:17:00
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
