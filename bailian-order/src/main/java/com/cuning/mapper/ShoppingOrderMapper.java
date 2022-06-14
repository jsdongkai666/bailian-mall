package com.cuning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cuning.bean.shoppingOrder.BailianOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created On : 2022/6/9.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: shoppingOrderMapper
 */
@Mapper
public interface ShoppingOrderMapper extends BaseMapper<BailianOrder> {

}
