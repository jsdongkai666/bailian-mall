package com.cuning.service;

import com.cuning.bean.shoppingOrder.BailianOrder;

import java.util.List;

/**
 * Created On : 2022/6/9.
 * <p>
 * Author     : lenovo
 * <p>
 * Description: 商品订单
 */
public interface ShoppingOrderService {


    /**
     * @author : wangdefeng
     * @date   : 2022/6/9
     * @param  : [com.cuning.bean.shoppingOrder.ShoppingOrder]
     * @return : java.lang.Boolean
     * @description : 新增订单
     */
    Boolean insertOrder(BailianOrder bailianOrder);

    /**
     * @author : wangdefeng
     * @date   : 2022/6/9
     * @param  : [com.cuning.bean.shoppingOrder.ShoppingOrder]
     * @return : java.lang.Boolean
     * @description : 批量删除订单
     */
    Boolean deleteListOrder(List<String> orderNos);


    /**
     * @author : wangdefeng
     * @date   : 2022/6/9
     * @param  : [java.lang.Integer]
     * @return : com.cuning.bean.shoppingOrder.ShoppingOrder
     * @description : 查询订单
     */
    BailianOrder selectOrderList(String orderNo);

    /**
     * @author : wangdefeng
     * @date   : 2022/6/9
     * @param  : [com.cuning.bean.shoppingOrder.ShoppingOrder]
     * @return : java.lang.Boolean
     * @description : 修改订单
     */
    Boolean updateOrder(BailianOrder bailianOrder);

    /**
     * @author : lixu
     * @date   : 2022/06/16
     * @param  : []
     * @return : java.lang.Integer
     * @description : 根据商品id，查询订单数量
     */
    Integer selectCount(String goodsId);
}
