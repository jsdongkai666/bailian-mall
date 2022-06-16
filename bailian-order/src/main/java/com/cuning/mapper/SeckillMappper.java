package com.cuning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cuning.bean.seckill.BailianSeckill;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created On : 2022/6/16.
 * <p>
 * Author     : lenovo
 * <p>
 * Description: SeckillMappper
 */
@Mapper
public interface SeckillMappper extends BaseMapper<BailianSeckill> {
}
