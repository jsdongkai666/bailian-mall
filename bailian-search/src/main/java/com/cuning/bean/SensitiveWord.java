package com.cuning.bean;

import lombok.Data;

/**
 * Created On : 2022/3/31.
 * <p>
 * Author : zhukang
 * <p>
 * Description: 敏感词
 */
@Data
public class
SensitiveWord {

    // 编号
    private Integer id;

    // 类型
    private String tid;

    // 敏感词
    private String word;
}
