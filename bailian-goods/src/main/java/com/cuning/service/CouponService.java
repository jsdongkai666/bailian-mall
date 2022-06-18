package com.cuning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.coupon.BailianCoupon;

import java.util.List;

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
    * @Date: 2022/6/17 
    * @Description: 判断用户领取的数量是否超过可重复领取的数量 
    */
    boolean repeatPick(String couponId);


    /**
    * @Param: [java.lang.String]
    * @return: boolean
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 减少优惠券库存
    */
    boolean subCouponAuantity(String couponId);



    /**
     * @author : wangdefeng
     * @date   : 2022/6/15
     * @param  : [java.util.List<com.cuning.bean.coupon.BailianCoupon>]
     * @return : java.lang.Boolean
     * @description : 增加优惠卷
     */
    Boolean insertNewCoupon(List<BailianCoupon> bailianCouponList);

    /**
     * @author : wangdefeng
     * @date   : 2022/6/15
     * @param  : [java.lang.String]
     * @return : java.lang.Boolean
     * @description : 删除优惠卷
     */
    Boolean deleteCoupon(String id);

    /**
     * @author : wangdefeng
     * @date   : 2022/6/15
     * @param  : [java.lang.String]
     * @return : java.lang.Boolean
     * @description : 查询优惠卷
     */
    BailianCoupon selectCoupon(String id);

    /**
     * @author : wangdefeng
     * @date   : 2022/6/15
     * @param  : [java.lang.String]
     * @return : java.lang.Boolean
     * @description : 更新优惠卷
     */
    Boolean updateCoupon(BailianCoupon bailianCoupon);

}
