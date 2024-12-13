/*
 * Copyright 2024 Jervis All Rights Reserved
 */
package xyz.muses.cache.param;

import xyz.muses.framework.cache.constant.ICacheEnum;
import xyz.muses.framework.cache.redis.param.BucketCacheParam;

/**
 * @author jervis
 * @date 2024/12/13
 */
public class UserParam extends BucketCacheParam {
    private static final long serialVersionUID = 6722376353289382242L;

    public UserParam(ICacheEnum cacheEnum, Long key) {
        super(cacheEnum, key);
    }
}
