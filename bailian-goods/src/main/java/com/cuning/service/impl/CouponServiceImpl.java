package com.cuning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.coupon.BailianCoupon;
import com.cuning.mapper.CouponMapper;
import com.cuning.service.CouponService;
import org.springframework.stereotype.Service;

/**
 * @author dengteng
 * @title: CouponServiceImpl
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/14
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, BailianCoupon> implements CouponService {
}
