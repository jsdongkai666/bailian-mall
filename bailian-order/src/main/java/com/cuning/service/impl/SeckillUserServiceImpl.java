package com.cuning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.seckill.BailianSeckillUser;
import com.cuning.mapper.SeckillUserMapper;
import com.cuning.service.SeckillUserService;
import org.springframework.stereotype.Service;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月16日 16:26:00
 */
@Service
public class SeckillUserServiceImpl extends ServiceImpl<SeckillUserMapper, BailianSeckillUser> implements SeckillUserService {
}
