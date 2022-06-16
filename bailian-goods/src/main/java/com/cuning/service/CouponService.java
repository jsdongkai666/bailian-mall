package com.cuning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.coupon.BailianCoupon;

/**
 * @author dengteng
 * @title: CouponService
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/14
 */
public interface CouponService extends IService<BailianCoupon> {

    /**
     * @Param: [java.lang.String]
     * @return: boolean
     * @Author: dengteng
     * @Date: 2022/6/15
     * @Description: 判断优惠券是否有效
     */
    String couponIsEfficient(String couponId);


    /**
    * @Param: [java.lang.String]
    * @return: boolean
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 减少优惠券库存
    */
    boolean subCouponAuantity(String couponId);


}
