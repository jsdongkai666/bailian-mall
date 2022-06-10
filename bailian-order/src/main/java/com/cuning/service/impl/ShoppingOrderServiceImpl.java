package com.cuning.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.shoppingOrder.BailianOrder;
import com.cuning.mapper.ShoppingOrderMapper;
import com.cuning.service.ShoppingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Boolean insertOrder(BailianOrder bailianOrder) {
        return shoppingOrderMapper.insert(bailianOrder)>0;
    }

    @Override
    public Boolean deleteListOrder(List<Integer> orderNos) {
        if (!orderNos.isEmpty()&&orderNos.size()==0) {
            return false;
        }else{
            QueryWrapper<BailianOrder> orderWrapper = new QueryWrapper<>();
            orderWrapper.in("order_no",orderNos);
            return  shoppingOrderMapper.delete(orderWrapper)>0;
        }
    }

    @Override
    public List<BailianOrder>  selectOrderList(String orderNo) {
        Page<BailianOrder> page = new Page<>(1, 3);
        QueryWrapper<BailianOrder> orderWrapper = new QueryWrapper<>();
        orderWrapper.like("order_no","%"+orderNo+"%");
        IPage<BailianOrder> pageParam = shoppingOrderMapper.selectPage(page,orderWrapper);

        return pageParam.getRecords();
    }

    @Override
    public Boolean updateOrder(BailianOrder bailianOrder) {
        return shoppingOrderMapper.updateById(bailianOrder) > 0;
    }


}
