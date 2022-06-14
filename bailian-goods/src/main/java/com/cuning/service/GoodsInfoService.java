package com.cuning.service;
/*
 * @Created on : 2022/6/9 0009
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsInfoService
 **/

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.goods.BailianGoodsInfo;

import java.util.List;
import java.util.Map;

public interface GoodsInfoService extends IService<BailianGoodsInfo> {

    BailianGoodsInfo saveGoods (BailianGoodsInfo goodsInfo);

    Page<BailianGoodsInfo> queryGoodsInfoPage(Integer pageNo, Integer pageSize);

    Boolean updateGoodsInfo(BailianGoodsInfo goodsInfo);

    Boolean deleteGoodsInfo(Integer goodsId);

    List<BailianGoodsInfo> selectGoodsByGoodsCategoryId(Integer categoryId);

    List<Integer> selectGoodsCategoryIds();

    BailianGoodsInfo queryGoodsInfoById(Integer goodsId);

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.lang.Boolean
    * @Author: dengteng
    * @Date: 2022/6/13
    * @Description: 设置到货提醒
    */
    Map<String, String> setArrivalReminders(String userId, String goodsId);

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.lang.Boolean
    * @Author: dengteng
    * @Date: 2022/6/13
    * @Description: 取消到货提醒
    */
    Boolean cancelArrivalReminders(String userId,String goodsId);

    /**
    * @Param: [java.lang.String, java.lang.Integer]
    * @return: java.lang.Boolean
    * @Author: dengteng
    * @Date: 2022/6/13
    * @Description: 商品补货
    */
    Boolean replenishment(String goodsId, Integer stockNum);

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/13
    * @Description: 收藏商品
    */
    Map<String, String> collectGoods(String userId, String goodsId);

    /**
    * @Param: [java.lang.String]
    * @return: java.util.List<java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/13
    * @Description: 根据用户编号获取用户的收藏列表
    */
    List<Object> getCollectListByUserId(String userId);


}
