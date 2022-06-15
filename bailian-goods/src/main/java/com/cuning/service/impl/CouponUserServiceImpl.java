package com.cuning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.coupon.BailianCouponUser;
import com.cuning.mapper.CouponUserMapper;
import com.cuning.service.CouponUserService;
import org.springframework.stereotype.Service;

/**
 * @author dengteng
 * @title: CouponUserServiceImpl
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/14
 */
@Service
public class CouponUserServiceImpl extends ServiceImpl<CouponUserMapper, BailianCouponUser> implements CouponUserService {
}
