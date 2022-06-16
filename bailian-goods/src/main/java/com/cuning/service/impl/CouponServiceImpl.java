package com.cuning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.coupon.BailianCoupon;
import com.cuning.mapper.CouponMapper;
import com.cuning.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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

    @Autowired(required = false)
    private CouponMapper couponMapper;


    @Override
    public Boolean insertNewCoupon(List<BailianCoupon> bailianCouponList) {
        for(BailianCoupon bailianCoupon:bailianCouponList){
            couponMapper.insert(bailianCoupon);
        }
        return true;
    }

    @Override
    public Boolean deleteCoupon(String id) {
        return couponMapper.deleteById(id)>0;
    }

    @Override
    public BailianCoupon selectCoupon(String id) {
        return couponMapper.selectById(id);
    }

    @Override
    public Boolean updateCoupon(BailianCoupon bailianCoupon) {
        BailianCoupon bailianCoupon1 = selectCoupon(bailianCoupon.getId());
        if(bailianCoupon1==null){
            return false;
        }
        if(bailianCoupon.getName()==""){
            bailianCoupon.setName(bailianCoupon1.getName());
        }
        if(bailianCoupon.getCouponType()<=0){
            bailianCoupon.setCouponType(bailianCoupon1.getCouponType());
        }
        if(bailianCoupon.getCouponAmount()<=0){
            bailianCoupon.setCouponAmount(bailianCoupon1.getCouponAmount());
        }
        if(bailianCoupon.getFullAmount()<=0){
            bailianCoupon.setFullAmount(bailianCoupon1.getFullAmount());
        }
        if(bailianCoupon.getQuantity()<=0){
            bailianCoupon.setQuantity(bailianCoupon1.getQuantity());
        }
        if(bailianCoupon.getRepeatQuantity()<=0){
            bailianCoupon.setRepeatQuantity(bailianCoupon1.getRepeatQuantity());
        }
        return couponMapper.updateById(bailianCoupon)>0;
    }
}
