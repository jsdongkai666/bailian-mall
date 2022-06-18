package com.cuning.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Created On : 2022/3/31.
 * <p>
 * Author : zhukang
 * <p>
 * Description: 敏感词
 */
@Data
public class SensitiveWord {

    // 编号
    @TableId(type = IdType.AUTO)
    private Integer id;

    // 类型
    private Integer tid;

    // 敏感词
    private String word;
}
