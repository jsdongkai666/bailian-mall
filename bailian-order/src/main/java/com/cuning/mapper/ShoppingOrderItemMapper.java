package com.cuning.mapper; /*
 * @Created on : 2022/6/15 0015
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: ShoppingOrderItemMapper
 **/

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cuning.bean.shoppingOrder.BailianOrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingOrderItemMapper extends BaseMapper<BailianOrderItem> {
}
