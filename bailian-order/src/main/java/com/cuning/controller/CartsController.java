package com.cuning.controller;

import com.cuning.bean.shoppingcarts.BailianCarts;
import com.cuning.service.CartsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
