package com.cuning.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cuning.bean.logistics.LogisticsCode;
import com.cuning.bean.logistics.LogisticsInfo;
import com.cuning.bean.shoppingOrder.BailianOrder;
import com.cuning.constant.CommonConstant;
import com.cuning.constant.KdConstant;
import com.cuning.constant.KdEnums;
import com.cuning.service.KdService;
import com.cuning.service.OrderFeignService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.cuning.constant.KdConstant.*;
import static com.cuning.util.HttpClient4Util.getResponse4PostByMap;
import static com.cuning.util.KdUtils.encrypt;
import static com.cuning.util.KdUtils.urlEncoder;


/**
 * @author dengteng
 * @title: KdServiceImpl
 * @projectName logisticsApi
 * @description: TODO
 * @date 2022/6/9
 */
@Service
public class KdServiceImpl implements KdService {

    @Autowired
    private OrderFeignService orderFeignService;

    @Override
    public LogisticsInfo queryLogisticsInfo(String ShipperCode, String LogisticCode) throws Exception {
        // 组装应用级参数
        String RequestData = "{" +
                "'CustomerName': ''," +
                "'OrderCode': ''," +
                "'ShipperCode': '" + ShipperCode + "'," +
                "'LogisticCode': '" + LogisticCode + "'," +
                "}";
        // 组装系统级参数
        Map<String, Object> params = new HashMap();
        params.put("RequestData", urlEncoder(RequestData, "UTF-8"));
        params.put("EBusinessID", KdConstant.EBusinessID);
        params.put("RequestType", "1002");//免费即时查询接口指令1002/在途监控即时查询接口指令8001/地图版即时查询接口指令8003
        String dataSign = encrypt(RequestData, KdConstant.ApiKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");
        // 以form表单形式提交post请求，post请求体中包含了应用级参数和系统级参数
        //String result=sendPost(ReqURL, params);

        String response4PostByMap = getResponse4PostByMap(KdConstant.ReqURL, params, "utf-8");

        JSONObject jsonObject = JSONObject.parseObject(response4PostByMap);
        LogisticsInfo logisticsInfo = JSONObject.toJavaObject(jsonObject, LogisticsInfo.class);
        logisticsInfo.setStateMsg(KdEnums.getMsgByCode(logisticsInfo.getState()));

        //System.out.println(logisticsInfo.getTraces());

        //根据公司业务处理返回的信息......
        return logisticsInfo;
    }

    @Override
    public LogisticsCode getCodeFromConstantList(String orderNo) {
        LogisticsCode logisticsCode = TRACKINGNUMBERLIST.get(new Random().nextInt(TRACKINGNUMBERLIST.size()));
        logisticsCode.setOrderNo(orderNo);
        return logisticsCode;
    }

    @Override
    public Map<String, String> queryOrderStatus(String orderNo) {

        Map<String, String> result = new HashMap<>();
        BailianOrder bailianOrder = orderFeignService.selectOrder(orderNo);
        if (bailianOrder == null) {
            result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            result.put("msg", "订单不存在");
            return result;
        }

        if (bailianOrder.getPayStatus() != 1 ) {
            result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            result.put("msg", "订单未支付");
            return result;
        }

        if (bailianOrder.getOrderStatus() < 1 ) {
            result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            result.put("msg", "订单未支付");
            return result;
        }

        if (bailianOrder.getOrderStatus() > 1 ) {
            result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            result.put("msg", "订单已发货");
            return result;
        }

        // 支付成功
        if (bailianOrder.getPayStatus() == 1 && bailianOrder.getOrderStatus() == 1) {
            result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            result.put("msg", "订单已发货");
            return result;
        }

        result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
        result.put("msg", "订单信息有误");
        return result;

    }

    @Override
    public LogisticsCode orderShip(String orderNo) {

        BailianOrder bailianOrder = orderFeignService.selectOrder(orderNo);

        LogisticsCode logisticsCode = TRACKINGNUMBERLIST.get(new Random().nextInt(TRACKINGNUMBERLIST.size()));
        //logisticsCode.setOrderNo(bailianOrder.getOrderNo());

        bailianOrder.setLogisticCode(logisticsCode.getLogisticCode());
        bailianOrder.setShipperCode(logisticsCode.getShipperCode());
        // 已发货
        bailianOrder.setOrderStatus(3);

        if (orderFeignService.updateOrder(bailianOrder)) {
            return logisticsCode;
        }
        return null;
    }

}
