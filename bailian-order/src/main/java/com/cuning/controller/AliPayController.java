package com.cuning.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cuning.bean.shoppingcarts.BailianCarts;
import com.cuning.service.AlipayService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author dengteng
 * @title: AliPayController
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/10
 */
@RestController
@Slf4j
public class AliPayController {

    //支付成功后要跳转的页面
    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Autowired
    private AlipayService alipayService;

    /** 
    * @Param: [java.lang.String, java.lang.String, java.lang.String] 
    * @return: java.lang.String 
    * @Author: dengteng
    * @Date: 2022/6/10 
    * @Description: 支付宝支付 订单名称，订单编号，总金额
    */
    @SneakyThrows
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(@RequestParam String subject, @RequestParam String orderNo, @RequestParam String totalfee) {

        AlipayTradePagePayResponse response = Factory.Payment
                //选择网页支付平台
                .Page()
                //调用支付方法:订单名称、订单号、金额、回调页面
                .pay(subject, orderNo, totalfee, returnUrl);
        //这里可以加入业务代码：比如生成订单等等。。
        System.out.println(response.body);
        return response.body;
    }

    @RequestMapping("/return")
    public String returnMeg(HttpServletRequest request) {
        //log.info("支付宝回掉");
        //Map<String, String> map = new HashMap();
        //Enumeration<String> parameterNames = request.getParameterNames();
        //while (parameterNames.hasMoreElements()) {
        //    String key = parameterNames.nextElement();
        //    String value = request.getParameter(key);
        //    map.put(key, value);
        //}
        ////验签
        //try {
        //    if (Factory.Payment.Common().verifyNotify(map)) {
        //        //验证用户的支付结果
        //        String trade_status = request.getParameter("trade_status");
        //        if ("TRADE_SUCCESS".equals(trade_status)) {
        //            //这里可以更新订单的状态等等。。
        //            alipayService.sendMsgToQueue();
        //        }
        //    } else {
        //        return "fail";
        //    }
        //} catch (Exception e) {
        //    return "fail";
        //}
        //return "success";
        return "success";
    }

   /** 
   * @Param: [javax.servlet.http.HttpServletRequest] 
   * @return: java.lang.String 
   * @Author: dengteng
   * @Date: 2022/6/10 
   * @Description: 支付宝支付成功回调接口 
   */
    @RequestMapping(value = "/notify")
    public String notifyAsync(HttpServletRequest request) {

        log.info("支付宝回掉");
        Map<String, String> map = new HashMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            map.put(key, value);
        }

        alipayService.sendMsgToQueue();

        //验签
        try {
            if (Factory.Payment.Common().verifyNotify(map)) {
                //验证用户的支付结果
                String trade_status = request.getParameter("trade_status");
                System.out.println("trade_status"+trade_status);
                if ("TRADE_SUCCESS".equals(trade_status)) {
                    //这里可以更新订单的状态等等。。
                    alipayService.sendMsgToQueue();
                }else {
                    return "fail";
                }
            } else {
                return "fail";
            }
        } catch (Exception e) {
            return "fail";
        }
        return "success";
    }

}
