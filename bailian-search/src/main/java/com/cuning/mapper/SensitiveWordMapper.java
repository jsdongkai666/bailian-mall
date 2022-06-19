package com.cuning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cuning.bean.SensitiveWord;
import org.apache.ibatis.annotations.Mapper;


/**
 * Created On : 2022/3/31.
 * <p>
 * Author : zhukang
 * <p>
 * Description: 敏感词持久层接口，使用mybatis-plus
 */
@Mapper
public interface SensitiveWordMapper extends BaseMapper<SensitiveWord> {
}
