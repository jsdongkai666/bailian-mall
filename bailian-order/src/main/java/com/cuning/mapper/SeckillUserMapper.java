package com.cuning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cuning.bean.seckill.BailianSeckillUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created On : 2022/6/16.
 * <p>
 * Author     : lenovo
 * <p>
 * Description: SeckillUserMapper
 */
@Mapper
public interface SeckillUserMapper extends BaseMapper<BailianSeckillUser> {
}
