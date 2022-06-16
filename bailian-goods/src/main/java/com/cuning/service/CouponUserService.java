package com.cuning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.coupon.BailianCouponUser;

import java.util.List;
import java.util.Map;

/**
 * @author dengteng
 * @title: CouponUserService
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/14
 */
public interface CouponUserService extends IService<BailianCouponUser> {

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: boolean
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 用户领取优惠券
    */
    boolean getCoupon(String userId,String couponId);

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.util.List<com.cuning.bean.coupon.BailianCouponUser>
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 查询用户优惠券列表
    */
    List<BailianCouponUser> getUserCouponList(String userId,String status);


    /**
    * @Param: []
    * @return: boolean
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 修改所有用户已经过期的优惠券的状态
    */
    boolean editUserCouponStatus();

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.util.List<com.cuning.bean.coupon.BailianCouponUser>
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 通过orderitem获取商品信息展示符合规则的优惠券列表
    */
    List<BailianCouponUser> getUserCouponListByGoods(String userId,String orderItemId);

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 用户使用优惠券
    */
    Map<String,String> useCoupon(String userId,String orderItemId,String couponId);

}
