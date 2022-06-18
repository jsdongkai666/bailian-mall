package com.cuning.controller;

import com.cuning.bean.shoppingOrder.BailianOrder;
import com.cuning.service.ShoppingOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created On : 2022/6/9.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: OrderController
 */
@RestController
@Api(tags = "订单操作入口")
public class OrderController {


    @Autowired
    private ShoppingOrderService shoppingOrderService;


    @GetMapping("/selectOrder")
    @ApiOperation(value = "查询订单", notes = "订单号查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单号",paramType = "query")
    })
    public List<BailianOrder> selectOrder(@RequestParam(value = "orderNo",required = false) String orderNo,
                                          @RequestParam(value = "pageNo") Integer pageNo,
                                          @RequestParam(value = "pageSize") Integer pageSize){
        return shoppingOrderService.selectOrderList(orderNo,pageNo,pageSize);
    }


    @PostMapping("/insertOrder")
    @ApiOperation(value = "插入订单", notes = "订单插入")
    public Boolean insertOrder(@RequestBody BailianOrder bailianOrder){
        return shoppingOrderService.insertOrder(bailianOrder);
    }


    @PostMapping("/updateOrder")
    @ApiOperation(value = "修改订单")
    public Boolean updateOrder(@RequestBody BailianOrder bailianOrder){
        return shoppingOrderService.updateOrder(bailianOrder);
    }


    @PostMapping("/deleteOrder")
    @ApiOperation(value = "删除订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号",allowMultiple = true,required = true,paramType = "query")
    })
    public Boolean updateOrder(@RequestParam("orderNo")List<String> orderNos){
        return shoppingOrderService.deleteListOrder(orderNos);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/16
     * @param  : [java.lang.String]
     * @return : java.lang.Integer
     * @description : 根据商品id，查询商品的销量
     */
    @GetMapping("/selectOrderCount")
    @ApiOperation(value = "查询商品销量", notes = "根据商品id，查询商品的销量")
    public Integer selectOrderCount(@RequestParam("goodsId") String goodsId){
        return shoppingOrderService.selectCount(goodsId);
    }



}
