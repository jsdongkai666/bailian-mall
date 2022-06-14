package com.cuning.controller;


import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.service.RelatedService;
import com.cuning.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created On : 2022/06/09.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 猜你喜欢操作入口
 */
@Slf4j
@RestController
@Api(tags = "猜你喜欢操作入口")
public class RelatedController {

    @Autowired(required = false)
    private RelatedService relatedService;


    /**
     * @author : lixu
     * @date   : 2022/06/10
     * @param  : [java.util.List<java.lang.Integer>]
     * @return : java.util.List<com.cuning.bean.BailianGoodsInfo>
     * @description : 猜你喜欢
     */
    @GetMapping("/goodsRelated")
    @ApiOperation(value = "猜你喜欢",notes = "根据用户的足迹，猜你喜欢")
    public List<BailianGoodsInfo> GoodsRelated(@RequestParam("userId") String userId){

        // 查询足迹中的商品
        List<BailianGoodsInfo> bailianGoodsInfoList = relatedService.selectFootPrintGoods(userId);
        log.info("------ 用户：{}，足迹中的商品：{} ------",userId,bailianGoodsInfoList);

        // 猜你喜欢的商品列表
        List<BailianGoodsInfo> relatedInfos = new ArrayList<>();

        // 随机数
        Random random = new Random();
        // 为你推荐的随机商品
        BailianGoodsInfo randomGoods;

        // 没有足迹时，猜你喜欢的商品
        // 获取商品所有分类id
        if (bailianGoodsInfoList.isEmpty()) {
            // list集合，存放商品分类id
            List<Integer> list = relatedService.selectGoodsCategoryIds();
            // id去重
            List<Integer> goodsCategoryIdList = list.stream().distinct().collect(Collectors.toList());
            log.info("------ 所有商品的分类id：{} ------",goodsCategoryIdList);

            List<BailianGoodsInfo> bailianGoodsInfos = relatedService.selectGoodsByCategoryId(goodsCategoryIdList.get(random.nextInt(goodsCategoryIdList.size())));
            for (int i = 0; relatedInfos.size() < 5 && relatedInfos.size() <= bailianGoodsInfos.size();) {
                randomGoods = bailianGoodsInfos.get(random.nextInt(bailianGoodsInfos.size()));
                if (!relatedInfos.contains(randomGoods)){
                    relatedInfos.add(i,randomGoods);
                    i++;
                }
            }
            log.info("------ 用户：{}，猜你喜欢：{} ------",userId,relatedInfos);
            return relatedInfos;
        }

        // 当足迹为1时
        if(bailianGoodsInfoList.size() == 1) {
            Integer goodsCategoryId = bailianGoodsInfoList.get(0).getGoodsCategoryId();
            List<BailianGoodsInfo> bailianGoodsInfos = relatedService.selectGoodsByCategoryId(goodsCategoryId);
            for (int i = 0; i < 5;) {
                randomGoods = bailianGoodsInfos.get(random.nextInt(bailianGoodsInfos.size()));
                if (!relatedInfos.contains(randomGoods)){
                    relatedInfos.add(i,randomGoods);
                    i++;
                }
            }
            log.info("------ 用户：{}，猜你喜欢：{} ------",userId,relatedInfos);
            return relatedInfos;
        }

        // 当足迹大于1时
        // 足迹中前两个商品的分类id
        Integer categoryId1 = bailianGoodsInfoList.get(0).getGoodsCategoryId();
        Integer categoryId2 = bailianGoodsInfoList.get(1).getGoodsCategoryId();
        // 相同，则推荐同分类下的商品
        if (categoryId1.equals(categoryId2)) {
            List<BailianGoodsInfo> bailianGoodsInfos = relatedService.selectGoodsByCategoryId(categoryId1);
            for (int i = 0; relatedInfos.size() < 5;) {
                randomGoods = bailianGoodsInfos.get(random.nextInt(bailianGoodsInfos.size()));
                if (!relatedInfos.contains(randomGoods)){
                    relatedInfos.add(i,randomGoods);
                    i++;
                }
            }
        }
        // 不同，最新商品同分类推荐3个，第二个商品同分类推荐2个
        List<BailianGoodsInfo> bailianGoodsInfos1 = relatedService.selectGoodsByCategoryId(categoryId1);
        for (int i = 0; relatedInfos.size() < 3;) {
            randomGoods = bailianGoodsInfos1.get(random.nextInt(bailianGoodsInfos1.size()));
            if (!relatedInfos.contains(randomGoods)){
                relatedInfos.add(i,randomGoods);
                i++;
            }
        }
        List<BailianGoodsInfo> bailianGoodsInfos2 = relatedService.selectGoodsByCategoryId(categoryId2);
        for (int i = relatedInfos.size(); relatedInfos.size() < 5;) {
            randomGoods = bailianGoodsInfos2.get(random.nextInt(bailianGoodsInfos2.size()));
            if (!relatedInfos.contains(randomGoods)){
                relatedInfos.add(i,randomGoods);
                i++;
            }
        }
        log.info("------ 用户：{}，猜你喜欢：{} ------",userId,relatedInfos);
        return relatedInfos;
    }


}
