package com.cuning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.coupon.BailianCoupon;
import com.cuning.mapper.CouponMapper;
import com.cuning.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
