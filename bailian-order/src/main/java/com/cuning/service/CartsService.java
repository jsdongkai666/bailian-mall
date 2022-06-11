package com.cuning.service;


import com.cuning.bean.shoppingcarts.BailianCarts;

import java.util.List;

public interface CartsService {
    /**
     * 获取购物车列表
     */
    List<BailianCarts> getCartsList();

    boolean addCarts(BailianCarts bailianCarts);

    boolean deleteCartsById(List<String> id);

    boolean modifyCarts(BailianCarts bailianCarts);
}
