package com.cuning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.SensitiveWord;
import com.cuning.mapper.SensitiveWordMapper;
import com.cuning.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created On : 2022/4/28.
 * <p>
 * Author : zhukang
 * <p>
 * Description: SensitiveWordServiceImpl
 */
@Service
public class SensitiveWordServiceImpl extends ServiceImpl<SensitiveWordMapper,SensitiveWord> implements SensitiveWordService {

    @Autowired
    private SensitiveWordMapper sensitiveWordMapper;

    @Override
    public List<SensitiveWord> getSensitiveWords() {
        return sensitiveWordMapper.selectList(null);
    }



}
