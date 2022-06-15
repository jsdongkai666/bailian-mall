package com.cuning.service.order.impl;

import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.bean.shoppingcarts.BailianCartProducts;
import com.cuning.bean.shoppingcarts.BailianCarts;
import com.cuning.service.order.CartsFeignService;
import com.cuning.service.order.CartsService;
import com.cuning.service.order.GoodsFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartsServiceImpl implements CartsService {
    @Autowired
    private CartsFeignService cartsFeignService;

    @Autowired
    private GoodsFeignService goodsFeignService;

    @Override
    public List<BailianCarts> getCartsList(Integer pageNo, Integer pageSize, String userId) {
        return cartsFeignService.getCartListPageInfo(pageNo,pageSize,userId);
    }

    @Override
    public BailianGoodsInfo getGoodsDetail(String goodsId) {
        return goodsFeignService.getGoodsDetail(goodsId);
    }

    @Override
    public BailianCarts getCartsById(String userId) {
        return cartsFeignService.getCartsDetailById(userId);
    }

    @Override
    public boolean addCartsByUserId(BailianCarts bailianCarts) {
        return cartsFeignService.addCartsByUserId(bailianCarts);
    }

    @Override
    public boolean addCartsDetail(BailianCartProducts bailianCartProducts) {
        return cartsFeignService.addCartsDetail(bailianCartProducts);
    }
}
