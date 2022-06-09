package com.cuning.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "购物车模块")
public class CartsController {

    @GetMapping("/")
}
