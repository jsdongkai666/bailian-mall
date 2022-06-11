package com.cuning.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.BailianGoodsInfo;
import com.cuning.service.GoodsInfoService;
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

    @PostMapping("/addGoods")
    public BailianGoodsInfo saveGoods(@RequestBody BailianGoodsInfo goodsInfo){
        return goodsInfoService.saveGoods(goodsInfo);
    }

    @GetMapping("/queryGoodsPage")
    public Page<BailianGoodsInfo> queryGoodsInfoPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize, @RequestParam("goodsName") String goodsName){
        return  goodsInfoService.queryGoodsInfoPage(pageNo,pageSize,goodsName);
    }


    @RequestMapping("/updateGoods")
    public boolean updateGoodsInfo(@RequestBody Integer goodsId){
        BailianGoodsInfo goodsInfo = goodsInfoService.queryGoodsInfoById(goodsId);
        return goodsInfoService.updateGoodsInfo(goodsInfo);
    }

    @RequestMapping("/deleteGoods")
    public boolean deleteGoodsInfo(@RequestBody Integer goodsId){
        return goodsInfoService.deleteGoodsInfo(goodsId);
    }
}
