package com.cuning.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.BailianGoodsInfo;
import com.cuning.service.GoodsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public boolean deleteGoodsInfo(@RequestParam Integer goodsId){
        return goodsInfoService.deleteGoodsInfo(goodsId);
    }

    @GetMapping("/queryGoodsByGoodsCategoryId")
    public List<BailianGoodsInfo> queryGoodsByGoodCategoryId(@RequestParam Integer categoryId){
        return goodsInfoService.selectGoodsByGoodsCategoryId(categoryId);
    }

    @GetMapping("/queryGoodsCategoryIds")
    public List<Integer> queryGoodsCategoryIds(){
        return goodsInfoService.selectGoodsCategoryIds();
    }

    @GetMapping("/queryGoodsById")
    public BailianGoodsInfo queryGoodsById(@RequestParam Integer goodsId){
       return goodsInfoService.queryGoodsInfoById(goodsId);
    }

}
