package com.cuning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cuning.bean.shoppingcarts.BailianCarts;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartsMapper extends BaseMapper<BailianCarts> {

}
