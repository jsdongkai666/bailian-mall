package com.cuning.controller;

import com.cuning.bean.shoppingcarts.BailianCarts;
import com.cuning.service.CartsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "购物车模块")
public class CartsController {

    @Autowired
    CartsService cartsService;

    @ApiOperation(value = "查询购物车列表")
    @GetMapping("/getCartsList")
    public List<BailianCarts> getCartsList(){
        return cartsService.getCartsList();
    }

    @ApiOperation(value = "新增购物车信息")
    @PostMapping("/addCartsList")
    public boolean addCartsList(@RequestBody BailianCarts bailianCarts){
        return cartsService.addCarts(bailianCarts);
    }

    @ApiOperation(value = "删除购物车信息")
    @PostMapping("/delCartsList")
    public boolean deleteCartsByBatchIds(@RequestParam List<String> ids){
        return cartsService.deleteCartsById(ids);
    }

    @ApiOperation(value = "修改购物车信息")
    @PostMapping("modCarts")
    public boolean modifyCarts(@RequestBody BailianCarts bailianCarts){
        return cartsService.modifyCarts(bailianCarts);
    }
}
