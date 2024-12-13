/*
 * Copyright 2024 Jervis All Rights Reserved
 */
package xyz.muses.cache.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存自动刷新
 * 
 * @author jervis
 * @date 2024/11/4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheRefresh {

    Cache[] caches() default Cache.MEMBER_USER;

    Param param() default Param.NONE;

    int paramIndex() default 0;

    /** 缓存 **/
    enum Cache {
        // 会员用户
        MEMBER_USER,
    }

    /** 参数 **/
    enum Param {

        // 无参
        NONE,

        // 主键
        ID,

        // 对象
        OBJ
    }
}
