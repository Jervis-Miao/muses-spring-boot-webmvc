/*
 * Copyright 2024 Jervis All Rights Reserved
 */
package xyz.muses.cache.aop.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.muses.cache.UserCache;
import xyz.muses.cache.aop.AbstractCacheRefreshProcessor;
import xyz.muses.cache.aop.CacheRefresh;
import xyz.muses.cache.param.UserParam;
import xyz.muses.constants.redis.cache.RedisCacheConst;

/**
 * @author jervis
 * @date 2024/11/4
 */
@Component("USER")
public class UserCacheProcessor extends AbstractCacheRefreshProcessor<Long> {

    @Autowired
    private UserCache userCache;

    @Override
    public boolean isLazy() {
        return true;
    }

    @Override
    protected void clear(Long userId) {
        if (null != userId) {
            userCache.delete(new UserParam(RedisCacheConst.Key.USER, userId));
        }
    }

    @Override
    protected Long readKeyFromArgs(CacheRefresh.Param param, Object[] args, int index) {
        switch (param) {
            case ID:
                if (args[index] instanceof Long) {
                    return (Long)args[index];
                }
            case OBJ:
            default:
                return null;
        }
    }
}
