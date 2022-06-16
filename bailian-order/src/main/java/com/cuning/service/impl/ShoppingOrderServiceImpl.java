package com.cuning.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.shoppingOrder.BailianOrder;
import com.cuning.bean.shoppingOrder.BailianOrderItem;
import com.cuning.mapper.ShoppingOrderItemMapper;
import com.cuning.mapper.ShoppingOrderMapper;
import com.cuning.service.ShoppingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created On : 2022/6/9.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: ShoppingOrderServiceImpl
 */
@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderService {


    @Autowired(required = false)
    private ShoppingOrderMapper shoppingOrderMapper;

    @Autowired(required = false)
    private ShoppingOrderItemMapper shoppingOrderItemMapper;

    @Override
    public Boolean insertOrder(BailianOrder bailianOrder) {
        bailianOrder.setUpdateTime(new Date());
        bailianOrder.setCreateTime(new Date());
        for (BailianOrderItem bailianOrderItem : bailianOrder.getBailianOrders()) {
            bailianOrderItem.setCreateTime(new Date());
            bailianOrderItem.setTotalPrice(Double.valueOf(bailianOrderItem.getSellingPrice() * bailianOrderItem.getGoodsCount()));
            shoppingOrderItemMapper.insert(bailianOrderItem);
        }
        return shoppingOrderMapper.insert(bailianOrder) > 0;
    }

    @Override
    public Boolean deleteListOrder(List<String> orderNos) {
        for (BailianOrder bailianOrder : shoppingOrderMapper.selectList(null)) {
            if (!orderNos.contains(bailianOrder.getOrderNo())) {
                return false;
            }
        }
        QueryWrapper<BailianOrderItem> orderItemQueryWrapper = new QueryWrapper<>();
        for (String orderNo : orderNos) {
            QueryWrapper<BailianOrder> orderWrapper = new QueryWrapper<>();
            orderWrapper.eq("order_no", orderNo);
            BailianOrder bailianOrders = shoppingOrderMapper.selectOne(orderWrapper);
            orderItemQueryWrapper.eq("order_id", bailianOrders.getOrderId());
            shoppingOrderItemMapper.delete(orderItemQueryWrapper);
        }
        QueryWrapper<BailianOrder> orderWrapper = new QueryWrapper<>();
        orderWrapper.in("order_no", orderNos);
        return shoppingOrderMapper.delete(orderWrapper) > 0;
    }

    @Override
    public BailianOrder selectOrderList(String orderNo) {
        Page<BailianOrder> page = new Page<>(1, 3);
        QueryWrapper<BailianOrder> orderWrapper = new QueryWrapper<>();
        orderWrapper.like("order_no", orderNo);
        IPage<BailianOrder> pageParam = shoppingOrderMapper.selectPage(page, orderWrapper);
        List<BailianOrder> bailianOrderList = pageParam.getRecords();

        QueryWrapper<BailianOrderItem> orderItemQueryWrapper = new QueryWrapper<>();
        orderItemQueryWrapper.eq("order_id", bailianOrderList.get(0).getOrderId());
        List<BailianOrderItem> bailianOrderItemList = shoppingOrderItemMapper.selectList(orderItemQueryWrapper);
        bailianOrderList.get(0).setBailianOrders(bailianOrderItemList);
        return bailianOrderList.get(0);
    }

    @Override
    public Boolean updateOrder(BailianOrder bailianOrder) {

        UpdateWrapper<BailianOrder> updateWrapper = new UpdateWrapper<>();
        if (!bailianOrder.getOrderNo().isEmpty()) updateWrapper.set("order_no", bailianOrder.getOrderNo());
        if (!bailianOrder.getUserId().isEmpty()) updateWrapper.set("user_id", bailianOrder.getUserId());
        if (bailianOrder.getTotalPrice() != 0) updateWrapper.set("total_price", bailianOrder.getTotalPrice());
        if (bailianOrder.getPayStatus() != 0) updateWrapper.set("pay_status", bailianOrder.getPayStatus());
        if (bailianOrder.getPayType() != 0) updateWrapper.set("pay_type", bailianOrder.getPayType());
        if (bailianOrder.getOrderStatus() != 0) updateWrapper.set("order_status", bailianOrder.getOrderStatus());
        if (!bailianOrder.getExtraInfo().isEmpty()) updateWrapper.set("extra_info", bailianOrder.getExtraInfo());
        if (!bailianOrder.getUserName().isEmpty()) updateWrapper.set("user_name", bailianOrder.getUserName());
        if (!bailianOrder.getUserPhone().isEmpty()) updateWrapper.set("user_phone", bailianOrder.getUserPhone());
        if (!bailianOrder.getUserAddress().isEmpty()) updateWrapper.set("user_address", bailianOrder.getUserAddress());
        if (bailianOrder.getIsDeleted() != 0) updateWrapper.set("is_deleted", bailianOrder.getIsDeleted());


        updateWrapper.eq("order_id", bailianOrder.getOrderId());
        return shoppingOrderMapper.update(null, updateWrapper) > 0;
    }

    @Override
    public Integer selectCount(String goodsId) {
        QueryWrapper<BailianOrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id",goodsId);
        List<BailianOrderItem> bailianOrderItems = shoppingOrderItemMapper.selectList(queryWrapper);
        Integer sales = 0;
        for (BailianOrderItem bailianOrderItem : bailianOrderItems) {
            sales += bailianOrderItem.getGoodsCount();
        }

        return sales;
    }


}
