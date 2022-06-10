package com.cuning.controller;

import com.cuning.constant.WechatPayConstant;
import com.cuning.service.WechatPayService;

import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengteng
 * @title: WeChatPayController
 * @projectName springcloud-93
 * @description: 微信支付操作入口
 * @date 2022/5/31
 */
@Slf4j
@RestController
@Api(tags = "微信支付操作入口")
public class WeChatPayController {

    @Autowired
    private WechatPayService wechatPayService;
    private PrintWriter out;

    /**
     * @Param: []
     * @return: com.dt.util.RequestResult<java.util.Map < java.lang.String, java.lang.String>>
     * @Author: dengteng
     * @Date: 2022/5/31
     * @Description: 请求微信官方，统一下单，返回支付链接
     */
    @ApiOperation(value = "统一下单",notes = "请求微信官方，统一下单，返回支付链接")
    @PostMapping("/unifiedorder")
    public Map<String, String> wechatPayUnifiedorderOrder(@RequestParam String body,
                                                                         @RequestParam Integer totalFree,
                                                                         @RequestParam String prodId) throws Exception {
        // 调用业务接口，生成请求微信官方支付系统，统一下与支付订单完整接口参数（格式是xml字符串）
        String unifiedOrderParamsXml = wechatPayService.wechatPayUnifieOrderParamsXml(body, totalFree, prodId);


        log.info("----- 1 请求微信官方，进行统一下单，接口参数:{} ------", unifiedOrderParamsXml);

        // 定义统一返回结果集合
        Map<String, String> resultMap = new HashMap<>();
        // 请求微信官方统一下单接口，下预支付订单
        Map<String, String> unifiedOrderResultMap = wechatPayService.wechatPayUnifiedOrder(unifiedOrderParamsXml);

        // 解析统一下预支付订单的结果，获取支付的二维码链接，此链接用于生成支付二维码给用户支付
        if (WechatPayConstant.WECHAT_PAY_SUCCESS.equals(unifiedOrderResultMap.get("return_code"))
                && WechatPayConstant.WECHAT_PAY_SUCCESS.equals(unifiedOrderResultMap.get("result_code"))) {
            // 交易类型
            resultMap.put("trade_type", unifiedOrderResultMap.get("trade_type"));
            // 预支付交易会话标识
            resultMap.put("prepay_id", unifiedOrderResultMap.get("prepay_id"));
            // 二维码链接code_url
            resultMap.put("code_url", unifiedOrderResultMap.get("code_url"));

            return resultMap;
        }

        // 统一返回支付失败结果
        resultMap.put("errCode",unifiedOrderResultMap.get("return_code"));
        resultMap.put("errMsg",unifiedOrderResultMap.get("return_msg"));

        // 返回支付失败结果
        return resultMap;


    }


    /**
    * @Param: [javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse]
    * @return: void
    * @Author: dengteng
    * @Date: 2022/5/31
    * @Description: 接受微信支付结果异步回调
    */
    @ApiOperation(value = "接受微信支付结果异步回调",notes = "接受微信支付结果异步回调")
    @RequestMapping("/wxpayResultBack")
    public void wechatPayResultBack(HttpServletRequest request, HttpServletResponse response) {
        // 支付完成后，微信会把相关支付结果及用户信息通过数据流的形式发送给商户，商户需要接收处理，并按文档规范返回应答。

        try (InputStream inputStream = request.getInputStream()){
            // 读取支付回调结果
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));


            // 逐行读取
            String readLine;


            // 定义返回的动态字符串对象
            StringBuilder wxpayResutlBackXml = new StringBuilder();

            while ((readLine = bufferedReader.readLine()) != null){
                wxpayResutlBackXml.append(readLine);
            }

            log.info("------- 2 微信官方，支付结果回调，支付结果:{} -------",wxpayResutlBackXml);

            // 解析异步回调支付结果，
            String wepayresultbackresponsexml = wechatPayService.wxchatPayNotifyResultResolve(wxpayResutlBackXml.toString());

            // 同步返回响应给微信官方
            PrintWriter out = response.getWriter();
            out.write(wepayresultbackresponsexml);
            out.flush();
            out.close();

            log.info("------- 3 微信官方，支付结果响应结果，响应结果结果:{} -------",wepayresultbackresponsexml);

        } catch (Exception e) {
            log.info("------- 4 微信官方，支付结果回调，异常:{} -------",e.getMessage());
        }

    }

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: com.dt.util.RequestResult<java.util.Map<java.lang.String,java.lang.String>>
    * @Author: dengteng
    * @Date: 2022/5/31
    * @Description: 查询订单
    */
    @ApiOperation(value = "查询订单",notes = "请求微信官方，查询订单接口，返回微信官方返回内容")
    @PostMapping("/orderquery")
    public RequestResult<Map<String, String>> orderquery(@RequestParam String outTradeNo) throws Exception {
        Map<String, String> map = wechatPayService.wxChatOrderQuery(outTradeNo);

        if (WechatPayConstant.WECHAT_PAY_SUCCESS.equals(map.get("return_code"))
                && WechatPayConstant.WECHAT_PAY_SUCCESS.equals(map.get("result_code"))
                && WechatPayConstant.WECHAT_PAY_SUCCESS.equals(map.get("trade_state"))) {
            return ResultBuildUtil.success(map);
        }

        return ResultBuildUtil.fail(map.get("return_code"),map.get("return_msg"));
    }


}
