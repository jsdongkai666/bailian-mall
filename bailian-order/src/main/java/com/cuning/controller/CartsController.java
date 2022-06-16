package com.cuning.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.shoppingcarts.BailianCartProducts;
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
    public List<BailianCarts> getCartsList(@RequestParam Integer pageNo,@RequestParam Integer pageSize,@RequestParam String userId){
        return cartsService.getCartsList(pageNo,pageSize,userId);
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
    @PostMapping("/modCarts")
    public boolean modifyCarts(@RequestBody BailianCarts bailianCarts){
        return cartsService.modifyCarts(bailianCarts);
    }

    @ApiOperation("根据用户id查询购物车详情")
    @GetMapping("/getCartsById")
    public BailianCarts getCartsDetailById(@RequestParam String userId){
        return cartsService.getCartsDetailByUserId(userId);
    }

    @ApiOperation("新增购物车详情")
    @PostMapping("/addCartsDetail")
    public boolean addCartsDetail(@RequestBody BailianCartProducts bailianCartProducts){
        return cartsService.addCartsDetail(bailianCartProducts);
    }

    @ApiOperation("删除购物车")
    @PostMapping("/deleteCartsByIds")
    public boolean deleteCartsByIds(@RequestParam List<String> ids){
        return cartsService.deleteCartsById(ids);
    }

    @ApiOperation("修改购物车")
    @PostMapping("/updateCartsInfo")
    public boolean updateCartsInfo(@RequestParam String id,@RequestParam Integer buyCount){
        return cartsService.modifyCartsById(id,buyCount);
    }


}
