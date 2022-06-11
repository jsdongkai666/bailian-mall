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
import com.cuning.bean.BailianGoodsInfo;

import java.util.List;

public interface GoodsInfoService extends IService<BailianGoodsInfo> {

    BailianGoodsInfo saveGoods (BailianGoodsInfo goodsInfo);

    Page<BailianGoodsInfo> queryGoodsInfoPage(Integer pageNo, Integer pageSize,String goodsName);

    BailianGoodsInfo queryGoodsInfoById(Integer goodsId);

    Boolean updateGoodsInfo(BailianGoodsInfo goodsInfo);

    Boolean deleteGoodsInfo(Integer goodsId);

    List<BailianGoodsInfo> selectGoodsByGoodsCategoryId(Integer categoryId);

    List<Integer>  selectGoodsCategoryIds();

}
