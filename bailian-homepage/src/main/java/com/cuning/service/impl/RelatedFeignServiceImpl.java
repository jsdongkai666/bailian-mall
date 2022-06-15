package com.cuning.service.impl;


import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.service.RelatedFeignService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : lixu
 * @date   : 2022/06/11
 * @param  :
 * @return :
 * @description : 熔断机制触发服务降级处理类
 */
@Service
public class RelatedFeignServiceImpl implements RelatedFeignService {

    @Override
    public List<BailianGoodsInfo> selectFootPrintGoods(String userId) {
        List<BailianGoodsInfo> bailianGoodsInfoList = new ArrayList<>();
        BailianGoodsInfo bailianGoodsInfo = BailianGoodsInfo.builder()
                .goodsId("10003").goodsName("无印良品 MUJI 基础润肤化妆水").goodsIntro("滋润型 400ml").goodsCategoryId(0)
                .goodsCoverImg("http://localhost:8093/uploadPic/87446ec4-e534-4b49-9f7d-9bea34665284.jpg")
                .goodsCarousel("http://localhost:8093/uploadPic/87446ec4-e534-4b49-9f7d-9bea34665284.jpg")
                .goodsDetailContent("<p>商品介绍加载中...</p>").originalPrice(100).sellingPrice(100).stockNum(1007)
                .goodsSellStatus(Byte.parseByte("1")).createUser(0).createTime(new Date("2019-09-18 13:18:47"))
                .updateUser(0).updateTime(new Date("2020-10-13 10:41:59")).build();
        bailianGoodsInfoList.add(bailianGoodsInfo);

        return bailianGoodsInfoList;
    }

    @Override
    public List<BailianGoodsInfo> selectGoodsByCategoryId(Integer categoryId) {
        List<BailianGoodsInfo> bailianGoodsInfoList = new ArrayList<>();
        BailianGoodsInfo bailianGoodsInfo = BailianGoodsInfo.builder()
                .goodsId("10003").goodsName("无印良品 MUJI 基础润肤化妆水").goodsIntro("滋润型 400ml").goodsCategoryId(0)
                .goodsCoverImg("http://localhost:8093/uploadPic/87446ec4-e534-4b49-9f7d-9bea34665284.jpg")
                .goodsCarousel("http://localhost:8093/uploadPic/87446ec4-e534-4b49-9f7d-9bea34665284.jpg")
                .goodsDetailContent("<p>商品介绍加载中...</p>").originalPrice(100).sellingPrice(100).stockNum(1007)
                .goodsSellStatus(Byte.parseByte("1")).createUser(0).createTime(new Date("2019-09-18 13:18:47"))
                .updateUser(0).updateTime(new Date("2020-10-13 10:41:59")).build();
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
        return BailianGoodsInfo.builder()
                .goodsId("10003").goodsName("无印良品 MUJI 基础润肤化妆水").goodsIntro("滋润型 400ml").goodsCategoryId(0)
                .goodsCoverImg("http://localhost:8093/uploadPic/87446ec4-e534-4b49-9f7d-9bea34665284.jpg")
                .goodsCarousel("http://localhost:8093/uploadPic/87446ec4-e534-4b49-9f7d-9bea34665284.jpg")
                .goodsDetailContent("<p>商品介绍加载中...</p>").originalPrice(100).sellingPrice(100).stockNum(1007)
                .goodsSellStatus(Byte.parseByte("1")).createUser(0).createTime(new Date("2019-09-18 13:18:47"))
                .updateUser(0).updateTime(new Date("2020-10-13 10:41:59")).build();
    }
}
