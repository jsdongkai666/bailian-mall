package com.cuning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.cuning.bean.SensitiveWord;
import com.cuning.mapper.SensitiveWordMapper;
import com.cuning.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created On : 2022/4/28.
 * <p>
 * Author : zhukang
 * <p>
 * Description: SensitiveWordServiceImpl
 */
@Service
public class SensitiveWordServiceImpl implements SensitiveWordService {

    @Autowired(required = false)
    private SensitiveWordMapper sensitiveWordMapper;

    @Override
    public List<SensitiveWord> getSensitiveWords() {
        return sensitiveWordMapper.selectList(null);
    }
}
