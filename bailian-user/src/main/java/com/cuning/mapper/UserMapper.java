package com.cuning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cuning.bean.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月09日 15:11:00
 */

public interface UserMapper extends BaseMapper<User> {

    int updateUserById(User user);
}
