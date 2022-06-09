package com.cuning.service.impl;

import com.cuning.bean.shoppingcarts.BailianCarts;
import com.cuning.mapper.CartsMapper;
import com.cuning.service.CartsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CartsServiceImpl  implements CartsService {

    @Autowired
    CartsMapper cartsMapper;

    @Override
    public List<BailianCarts> getCartsList() {
        return cartsMapper.selectList(null);
    }
}
