package com.cuning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cuning.bean.shoppingcarts.BailianCartProducts;
import com.cuning.bean.shoppingcarts.BailianCarts;
import com.cuning.mapper.CartProductsMapper;
import com.cuning.mapper.CartsMapper;
import com.cuning.service.CartsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CartsServiceImpl  implements CartsService {

    @Autowired
    CartsMapper cartsMapper;

    @Autowired
    CartProductsMapper cartProductsMapper;

    @Override
    public List<BailianCarts> getCartsList() {
        List<BailianCarts> carts = cartsMapper.selectList(null);
        for (BailianCarts bailianCarts:carts){
            List<BailianCartProducts> productsList = cartProductsMapper.selectList(new QueryWrapper<BailianCartProducts>().eq("cart_id",bailianCarts.getId()));
            bailianCarts.setProductsList(productsList);
        }
        return carts;
    }

    @Override
    public boolean addCarts(BailianCarts bailianCarts) {
        bailianCarts.setUpdatedAt(new Date());
        bailianCarts.setCreatedAt(new Date());
        List<BailianCartProducts> bailianCartProducts = bailianCarts.getProductsList();
        if (bailianCartProducts != null) {
            for (BailianCartProducts products : bailianCartProducts) {
                products.setCreatedAt(new Date());
                products.setUpdatedAt(new Date());
                cartProductsMapper.insert(products);
            }
        }
        return cartsMapper.insert(bailianCarts)>0;
    }

    @Override
    public boolean deleteCartsById(List<String> ids) {
        for (String id : ids) {
            List<BailianCartProducts> productsList = cartsMapper.selectById(id).getProductsList();
            for(int i = 0; i <productsList.size(); i++){
                cartProductsMapper.deleteById(productsList.get(i).getId());
            }
            cartsMapper.deleteById(id);
        }
        return true;
    }

    @Override
    public boolean modifyCarts(BailianCarts bailianCarts){
        bailianCarts.setUpdatedAt(new Date());
        if (bailianCarts.getProductsList()!=null) {
            for (BailianCartProducts bailianCartsProducts : bailianCarts.getProductsList()) {
                bailianCartsProducts.setUpdatedAt(new Date());
                cartProductsMapper.updateById(bailianCartsProducts);
            }
        }
       return cartsMapper.updateById(bailianCarts)>0;

    }
}
