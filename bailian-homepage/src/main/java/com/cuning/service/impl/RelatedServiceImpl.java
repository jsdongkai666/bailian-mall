package com.cuning.service.impl;


import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.service.RelatedService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lixu
 * @date   : 2022/06/11
 * @param  : 
 * @return : 
 * @description : 熔断机制触发服务降级处理类
 */
@Service
public class RelatedServiceImpl implements RelatedService {
    @Override
    public List<BailianGoodsInfo> selectFootPrintGoods(String userId) {
        List<BailianGoodsInfo> bailianGoodsInfoList = new ArrayList<>();
        BailianGoodsInfo bailianGoodsInfo = BailianGoodsInfo.builder()
                .goodsId(11111)
                .goodsCategoryId(23)
                .goodsDetailContent("触发熔断")
                .goodsIntro("GG了")
                .build();
        bailianGoodsInfoList.add(bailianGoodsInfo);

        return bailianGoodsInfoList;
    }

    @Override
    public List<BailianGoodsInfo> selectGoodsByCategoryId(Integer categoryId) {
        List<BailianGoodsInfo> bailianGoodsInfoList = new ArrayList<>();
        BailianGoodsInfo bailianGoodsInfo = BailianGoodsInfo.builder()
                .goodsId(11111)
                .goodsCategoryId(23)
                .goodsDetailContent("触发熔断")
                .goodsIntro("GG了")
                .build();
        bailianGoodsInfoList.add(bailianGoodsInfo);

        return bailianGoodsInfoList;
    }

    @Override
    public List<Integer> selectGoodsCategoryIds() {
        List<Integer> list =new ArrayList<>();
        list.add(0);
        return list;
    }

    @Override
    public BailianGoodsInfo selectGoodsById(Integer goodsId) {
        BailianGoodsInfo bailianGoodsInfo = BailianGoodsInfo.builder()
                .goodsId(11111)
                .goodsCategoryId(23)
                .goodsDetailContent("触发熔断")
                .goodsIntro("GG了")
                .build();
        return bailianGoodsInfo;
    }
}
