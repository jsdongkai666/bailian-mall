package com.cuning.controller.logistics;

import com.cuning.bean.logistics.LogisticsInfo;
import com.cuning.service.LogisticsFeignService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author dengteng
 * @title: LogisticsController
 * @projectName cuning-bailian
 * @description: 物流管理controller
 * @date 2022/6/14
 */
@Slf4j
@RestController
@Api(tags = "物流管理操作入口")
public class LogisticsController {

    @Autowired(required = false)
    private LogisticsFeignService logisticsFeignService;

    @ApiOperation(value = "查询快递信息",notes = "通过快递单号和快递公司编码获取快递信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logisticCode", value = "快递单号",paramType = "query"),
            @ApiImplicitParam(name = "shipperCode", value = "物流公司编码",paramType = "query")
    })
    @GetMapping("/getLogisticsInfo")
    public RequestResult<LogisticsInfo> getLogisticsInfo(@RequestParam("logisticCode") String logisticCode, @RequestParam("shipperCode") String shipperCode){
        LogisticsInfo logisticsInfo = logisticsFeignService.getlogisticsInfoByTrackingNumber(logisticCode, shipperCode);
        return ResultBuildUtil.success(logisticsInfo);
    }


    /**
    * @Param: [java.lang.String]
    * @return: com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.String>>
    * @Author: dengteng
    * @Date: 2022/6/17
    * @Description: 商品发货
    */
    @ApiOperation(value = "商品发货",notes = "商品发货返回快递单号和快递公司编码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号",paramType = "query")
    })
    @GetMapping("/goodsShipped")
    public RequestResult<Map<String,String>> goodsShipped(@RequestParam String orderNo){
        Map<String, String> ship = logisticsFeignService.ship(orderNo);
        return ResultBuildUtil.success(ship);
    }



}
