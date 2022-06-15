package com.cuning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsInfo;
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
    public List<BailianCarts> getCartsList(Integer pageNo,Integer pageSize,String userId) {
        Page<BailianCarts> page = new Page<>(pageNo,pageSize);
        Page<BailianCarts> bailianCarts = cartsMapper.selectPage(page, new QueryWrapper<BailianCarts>().eq("user_id", userId));
        List<BailianCarts> bailianCartsList = bailianCarts.getRecords();
        for (BailianCarts carts:bailianCartsList){
            List<BailianCartProducts> productsList = cartProductsMapper.selectList(new QueryWrapper<BailianCartProducts>().eq("cart_id",carts.getId()));
            carts.setProductsList(productsList);
        }
        return bailianCartsList;
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
            cartProductsMapper.deleteById(id);
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

    @Override
    public BailianCarts getCartsDetailByUserId(String userId) {
        BailianCarts carts = cartsMapper.selectOne(new QueryWrapper<BailianCarts>().eq("user_id", userId));
        List<BailianCartProducts> productsList = cartProductsMapper.selectList(new QueryWrapper<BailianCartProducts>().eq("cart_id",carts.getId()));
        carts.setProductsList(productsList);
        return carts;
    }

    @Override
    public boolean addCartsDetail(BailianCartProducts bailianCartProducts) {
        return cartProductsMapper.insert(bailianCartProducts)>0;
    }

    @Override
    public boolean modifyCartsById(String id, Integer buyCount) {
        BailianCartProducts bailianCartProducts = cartProductsMapper.selectById(id);
        bailianCartProducts.setUpdatedAt(new Date());
        bailianCartProducts.setBuyNum(buyCount);
        return cartProductsMapper.updateById(bailianCartProducts) > 0;
    }
}
