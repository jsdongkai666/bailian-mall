//package com.cuning.controller;
//
//import com.cuning.bean.shoppingOrder.BailianOrder;
//import com.cuning.service.ShoppingOrderService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.models.auth.In;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * Created On : 2022/6/9.
// * <p>
// * Author     : WangDeFeng
// * <p>
// * Description: OrderController
// */
//@RestController
//@Api(tags = "订单操作入口")
//public class OrderController {
//
//
//    @Autowired
//    private ShoppingOrderService shoppingOrderService;
//
//
//    @GetMapping("/selectOrder")
//    @ApiOperation(value = "查询订单", notes = "订单号查询")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "orderNo", value = "订单号",paramType = "query")
//    })
//    public BailianOrder selectOrder(@RequestParam(value = "orderNo",required = false) String orderNo){
//        return shoppingOrderService.selectOrderList(orderNo);
//    }
//
//
//    @PostMapping("/insertOrder")
//    @ApiOperation(value = "插入订单", notes = "订单插入")
//    public Boolean insertOrder(@RequestBody BailianOrder bailianOrder){
//        return shoppingOrderService.insertOrder(bailianOrder);
//    }
//
//
//    @PostMapping("/updateOrder")
//    @ApiOperation(value = "修改订单")
//    public Boolean updateOrder(@RequestBody BailianOrder bailianOrder){
//        return shoppingOrderService.updateOrder(bailianOrder);
//    }
//
//
//    @PostMapping("/deleteOrder")
//    @ApiOperation(value = "删除订单")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "orderNo", value = "订单编号",allowMultiple = true,required = true,paramType = "query")
//    })
//    public Boolean updateOrder(@RequestParam("orderNo")List<String> orderNos){
//        return shoppingOrderService.deleteListOrder(orderNos);
//    }
//
//
//}
