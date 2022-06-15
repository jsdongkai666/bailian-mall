package com.cuning.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.service.GoodsInfoService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @Created on : 2022/6/9 0009
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsInfoController
 **/
@Slf4j
@RestController
public class GoodsInfoController {

    @Autowired(required = false)
    private GoodsInfoService goodsInfoService;

    /***
     * @author : Administrator
     * @date   : 2022/6/10 0010
     * @param  : [com.cuning.bean.goods.BailianGoodsInfo]
     * @return : com.cuning.bean.goods.BailianGoodsInfo
     * @description : 新增商品
     */
    @PostMapping("/addGoods")
    public BailianGoodsInfo saveGoods(@RequestBody BailianGoodsInfo goodsInfo){
        return goodsInfoService.saveGoods(goodsInfo);
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/10 0010
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.String]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsInfo>
     * @description : 后台分页查询
     */
    @GetMapping("/queryGoodsPage")
    public Page<BailianGoodsInfo> queryGoodsInfoPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize, @RequestParam("goodsName") String goodsName){
        return  goodsInfoService.queryGoodsInfoPage(pageNo,pageSize,goodsName);
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/10 0010
     * @param  : [com.cuning.bean.goods.BailianGoodsInfo]
     * @return : com.cuning.bean.goods.BailianGoodsInfo
     * @description : 修改商品详情
     */
    @PostMapping("/updateGoods")
    public BailianGoodsInfo updateGoodsInfo(@RequestBody BailianGoodsInfo goodsInfo){
        Boolean flag = goodsInfoService.updateGoodsInfo(goodsInfo);
        if (flag){
            return  goodsInfo;
        }
       return null;
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/10 0010
     * @param  : [java.lang.Integer]
     * @return : boolean
     * @description :删除商品
     */
    @RequestMapping("/deleteGoods")
    public boolean deleteGoodsInfo(@RequestParam String goodsId){
        return goodsInfoService.deleteGoodsInfo(goodsId);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.lang.Integer]
     * @return : java.util.List<com.cuning.bean.goods.BailianGoodsInfo>
     * @description : 根据分类id，查询商品
     */
    @GetMapping("/queryGoodsByGoodsCategoryId")
    @ApiOperation(value = "查询商品",notes = "根据分类id，查询商品")
    public List<BailianGoodsInfo> queryGoodsByGoodCategoryId(@RequestParam Integer categoryId){
        return goodsInfoService.selectGoodsByGoodsCategoryId(categoryId);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : []
     * @return : java.util.List<java.lang.Integer>
     * @description : 查询商品的所有分类id
     */
    @GetMapping("/queryGoodsCategoryIds")
    @ApiOperation(value = "查询分类id",notes = "查询商品的所有分类id")
    public List<Integer> queryGoodsCategoryIds(){
        return goodsInfoService.selectGoodsCategoryIds();
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.lang.Integer]
     * @return : com.cuning.bean.goods.BailianGoodsInfo
     * @description : 根据id查询商品
     */
    @GetMapping("/queryGoodsById")
    @ApiOperation(value = "查询商品",notes = "根据id查询商品")
    public BailianGoodsInfo queryGoodsById(@RequestParam String goodsId){
       return goodsInfoService.queryGoodsInfoById(goodsId);
    }


}
