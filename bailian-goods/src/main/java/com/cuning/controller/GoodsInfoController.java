package com.cuning.controller;

import com.cuning.bean.GoodsInfo;
import com.cuning.service.GoodsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private GoodsInfoService goodsInfoService;

    @PostMapping("/addGoods")
    public GoodsInfo saveGoods(GoodsInfo goodsInfo){
        return goodsInfoService.saveGoods(goodsInfo);
    }
}
