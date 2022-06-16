package com.cuning.service.order;

import com.cuning.bean.shoppingOrder.BailianOrder;

/***
 * Created On : 2022/6/16.
 * <p>
 * Author     : kk
 * <p>
 * Description: 订单服务类
 */
public interface OrderService {
    boolean insertOrder(BailianOrder bailianOrder);
}
