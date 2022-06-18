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
import com.cuning.util.PageSupport;

import java.util.List;
import java.util.Map;

public interface GoodsInfoService extends IService<BailianGoodsInfo> {

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [com.cuning.bean.goods.BailianGoodsInfo, java.lang.String]
     * @return : com.cuning.bean.goods.BailianGoodsInfo
     * @description : 新增商品
     */
    BailianGoodsInfo saveGoods(BailianGoodsInfo goodsInfo, String userId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.lang.Integer]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsInfo>
     * @description : 分页查询商品
     */
    Page<BailianGoodsInfo> queryGoodsInfoPage(Integer pageNo, Integer pageSize);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [com.cuning.bean.goods.BailianGoodsInfo, java.lang.String]
     * @return : java.lang.Boolean
     * @description : 修改商品详情
     */
    Boolean updateGoodsInfo(BailianGoodsInfo goodsInfo, String userId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.String]
     * @return : java.lang.Boolean
     * @description : 删除商品
     */
    Boolean deleteGoodsInfo(String goodsId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.String, java.lang.Byte]
     * @return : java.lang.Boolean
     * @description : 上下架商品
     */
    Boolean updateGoodsSellStatus(String goodsId, Byte goodsSellStatus);

    /**
     * @param : [java.lang.Integer]
     * @return : java.util.List<com.cuning.bean.goods.BailianGoodsInfo>
     * @description : 根据分类id，查询该分类的所有商品
     */
    List<BailianGoodsInfo> selectGoodsByGoodsCategoryId(Integer categoryId);

    /**
     * @author : lixu
     * @date : 2022/06/15
     * @description : 根据分类id，查询该分类的所有商品
     */
    List<BailianGoodsInfo> selectGoodsByGoodsCategoryId(Integer categoryId);

    /**
     * @param : []
     * @return : java.util.List<java.lang.Integer>
     * @author : lixu
     * @date : 2022/06/15
     * @description : 查询所有的分类id
     */
    List<Integer> selectGoodsCategoryIds();

    /**
     * @param : [java.lang.String]
     * @return : com.cuning.bean.goods.BailianGoodsInfo
     * @author : lixu
     * @date : 2022/06/15
     * @description : 根据id，查询商品
     */
    BailianGoodsInfo queryGoodsInfoById(String goodsId);

    /**
     * @author : lixu
     * @date   : 2022/06/17
     * @param  : []
     * @return : java.util.List<java.lang.String>
     * @description : 查询所有商品id
     */
    List<String> queryGoodsIds();

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
    Boolean cancelArrivalReminders(String userId, String goodsId);

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
     * @return: java.util.Map<java.lang.String, java.lang.String>
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
    PageSupport getCollectListByUserId(String userId, String pageNo, String pageSize);


}
