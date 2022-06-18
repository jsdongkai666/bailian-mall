package com.cuning.service.order.impl;

import com.cuning.bean.shoppingOrder.BailianOrder;
import com.cuning.service.order.OrderFeignService;
import com.cuning.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderFeignService orderFeignService;

    @Override
    public boolean insertOrder(BailianOrder bailianOrder) {
        return orderFeignService.insertOrder(bailianOrder);
    }

    @Override
    public BailianOrder getOrderDetail(String orderNo) {
        return orderFeignService.getOrderDetail(orderNo);
    }

    @Override
    public boolean updateOrder(BailianOrder order) {
        return orderFeignService.updateOrder(order);
    }
}
