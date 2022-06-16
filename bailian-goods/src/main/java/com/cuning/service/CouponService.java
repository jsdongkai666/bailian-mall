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
