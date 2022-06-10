package com.cuning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cuning.bean.shoppingcarts.BailianCartProducts;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartProductsMapper extends BaseMapper<BailianCartProducts> {
}
