package com.cuning.util;

import lombok.Data;

/**
 * Created On : 2022/5/13.
 * <p>
 * Author : zhukang
 * <p>
 * Description: 统一返回结果实体类
 */
@Data
public class RequestResult<T> {

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态说明
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;
}
