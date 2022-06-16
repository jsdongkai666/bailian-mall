package com.cuning.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.shoppingcarts.BailianCartProducts;
import com.cuning.bean.shoppingcarts.BailianCarts;

import java.util.List;

public interface CartsService {
    /**
     * 获取购物车列表
     */
    List<BailianCarts> getCartsList(Integer pageNo,Integer pageSize,String userId);

    boolean addCarts(BailianCarts bailianCarts);

    boolean deleteCartsById(List<String> id);

    boolean modifyCarts(BailianCarts bailianCarts);

    BailianCarts getCartsDetailByUserId(String userId);

    boolean addCartsDetail(BailianCartProducts bailianCartProducts);

    boolean modifyCartsById(String id,Integer buyCount);
}
