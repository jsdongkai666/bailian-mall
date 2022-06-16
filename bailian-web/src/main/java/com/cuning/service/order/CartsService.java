package com.cuning.service.order;


import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.bean.shoppingcarts.BailianCartProducts;
import com.cuning.bean.shoppingcarts.BailianCarts;

import java.util.List;

public interface CartsService{
    List<BailianCarts> getCartsList(Integer pageNo, Integer pageSize, String userId);

    BailianGoodsInfo getGoodsDetail(String goodsId);

    BailianCarts getCartsById(String userId);

    boolean addCartsByUserId(BailianCarts bailianCarts);

    boolean addCartsDetail(BailianCartProducts bailianCartProducts);

    boolean batchDeleteCarts(List<String> ids);

    boolean updateCartsInfo(Integer buyCount,String id);

    List<BailianCartProducts> getCartProductByIds(List<String> cartsId);
}
