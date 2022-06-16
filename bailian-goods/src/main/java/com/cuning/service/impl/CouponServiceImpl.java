package com.cuning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.coupon.BailianCoupon;
import com.cuning.mapper.CouponMapper;
import com.cuning.service.CouponService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author dengteng
 * @title: CouponServiceImpl
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/14
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, BailianCoupon> implements CouponService {

    @Override
    public String couponIsEfficient(String couponId) {

        BailianCoupon coupon = this.getById(couponId);
        // 不存在
        if (coupon == null) {
            return "优惠券不存在";
        }

        // 过了发放日期
        Date date = new Date();
        if (date.compareTo(coupon.getEffectiveEndTime()) > 0) {
            return "优惠券领取日期已过";
        }

        // 没了
        if (coupon.getQuantity() <= 0) {
            return "优惠券数量不足";
        }

        if (coupon.getRepeatQuantity() <= 0) {
            return "优惠券领取太多了";
        }

        return "true";
    }

    @Override
    public boolean subCouponAuantity(String couponId) {
        BailianCoupon coupon = this.getById(couponId);
        coupon.setQuantity(coupon.getQuantity() - 1);
        return  this.updateById(coupon);
    }
}
