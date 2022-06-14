package com.cuning.service;

import java.util.Map;

public interface SeckillService {

    /**
     * @description 限制商品抢购的用户数
     * @author  tengjiaozhai
     * @updateTime 2022/6/7 15:54 No such property: code for class: Script1
     */
    boolean limitByUserCount(String prodId);

    /**
     * @description 初始化商品库存到redis
     * @author  tengjiaozhai
     * @updateTime 2022/6/7 16:30 No such property: code for class: Script1
     */
    void initProdStock2Redis(String prodId);

    /**
     * @description 校验商品库存是否大于购买数量
     * @author  tengjiaozhai
     * @updateTime 2022/6/7 16:39 No such property: code for class: Script1
     */
    boolean checkProdStockEnough(String prodId, Integer buyCount);

    /**
     * @description 当前抢购用户是否没有买过商品
     * @author  tengjiaozhai
     * @updateTime 2022/6/7 16:56 No such property: code for class: Script1
     */
    boolean checkSeckillUserBought(String userId,String prodId);

    boolean checkProdStockLocked(String prodId);

    /**
     * @description 扣减库存
     * @author  tengjiaozhai
     * @updateTime 2022/6/7 17:26 No such property: code for class: Script1
     */
    boolean subProdStock(String prodId,Integer buyCount);

    /**
     * @description 释放库存锁
     * @author  tengjiaozhai
     * @updateTime 2022/6/7 17:31 No such property: code for class: Script1
     */
    void unlockProdStock(String prodId);

    String createOrder(String userId,String prodId,Integer buyCount);

    Map<String,String> getOrderNoInfo(String userId, String tradeOrderNo);

    //支付抢购订单
    Map<String,String> payOrder(String userId,String prodId,String tradeOrderNo);

    String AliPayOrder(String userId, String prodId, String tradeOrderNo);
}
