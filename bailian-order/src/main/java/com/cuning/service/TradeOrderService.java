package com.cuning.service;

import java.util.Map;

/**
 * 订单业务接口
 */
public interface TradeOrderService {

    //生成交易订单
    void saveTradeOrder(Map<String, String> tradeOrder);

    Map<String,String> findTradeOrder(String userId,String orderId);
//    payTradeOrder
    Map<String,String> payTradeOrder(String userId,String prodId,String orderId) throws Exception;

    String aliPayTradeOrder(String userId, String prodId, String orderId) throws Exception;

}
