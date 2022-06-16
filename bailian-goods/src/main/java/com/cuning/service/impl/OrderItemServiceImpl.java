package com.cuning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.shoppingOrder.BailianOrderItem;
import com.cuning.mapper.OrderItemMapper;
import com.cuning.service.OrderItemService;
import org.springframework.stereotype.Service;

/**
 * @author dengteng
 * @title: OrderItemServiceImpl
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/15
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, BailianOrderItem> implements OrderItemService {
}
