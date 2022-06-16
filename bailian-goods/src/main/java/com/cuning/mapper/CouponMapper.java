package com.cuning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cuning.bean.coupon.BailianCoupon;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dengteng
 * @title: CouponMapper
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/14
 */
@Mapper
public interface CouponMapper extends BaseMapper<BailianCoupon> {
}
