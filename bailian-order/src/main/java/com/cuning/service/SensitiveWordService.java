package com.cuning.service;



import com.cuning.bean.SensitiveWord;

import java.util.List;

/**
 * Created On : 2022/4/28.
 * <p>
 * Author : zhukang
 * <p>
 * Description: 敏感词业务接口
 */
public interface SensitiveWordService {

    /**
     * @author : zhukang
     * @date   : 2022/4/28
     * @param  : []
     * @return : java.util.Set<java.lang.String>
     * @description : 查询所有的敏感词
     */
    List<SensitiveWord> getSensitiveWords();
}
