package com.cuning.annotation;

import java.lang.annotation.*;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月11日 15:49:00
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckToken {
}
