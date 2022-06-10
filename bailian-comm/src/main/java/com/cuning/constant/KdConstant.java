package com.cuning.constant;

import com.cuning.bean.logistics.LogisticsCode;

import java.util.*;

/**
 * @author dengteng
 * @title: KdConstant
 * @projectName logisticsApi
 * @description: TODO
 * @date 2022/6/9
 */
public class KdConstant {


    //用户ID，快递鸟提供，注意保管，不要泄漏
    public static final String EBusinessID = "1765289";//即用户ID，登录快递鸟官网会员中心获取 https://www.kdniao.com/UserCenter/v4/UserHome.aspx
    //API key，快递鸟提供，注意保管，不要泄漏
    public static final String ApiKey = "c13b7cd1-695c-4535-86dc-3ce282631988";//即API key，登录快递鸟官网会员中心获取 https://www.kdniao.com/UserCenter/v4/UserHome.aspx
    //请求url, 正式环境地址
    public static final String ReqURL = "https://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";

    /**
     * 圆通快递编码
     */
    public static final String YT = "YTO";
    /**
     * 百世快递编码
     */
    public static final String BS = "HTKY";
    /**
     * 申通快递编码
     */
    public static final String ST = "STO";
    /**
     * 极兔快递编码
     */
    public static final String JT = "JTSD";


    public static final List<LogisticsCode> TRACKINGNUMBERLIST = Arrays.asList(
            LogisticsCode.builder().logisticCode("JT5121556066300").shipperCode("JTSD").build(),
            LogisticsCode.builder().logisticCode("YT6453812778495").shipperCode("YTO").build(),
            LogisticsCode.builder().logisticCode("777085939426240").shipperCode("STO").build(),
            LogisticsCode.builder().logisticCode("JT5121556066300").shipperCode("JTSD").build(),
            LogisticsCode.builder().logisticCode("YT6491452208870").shipperCode("YTO").build(),
            LogisticsCode.builder().logisticCode("JT3004471535683").shipperCode("JTSD").build());
}
