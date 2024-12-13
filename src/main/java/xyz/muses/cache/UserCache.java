/*
 * Copyright 2024 Jervis All Rights Reserved.
 */

package xyz.muses.cache;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.muses.cache.param.UserParam;
import xyz.muses.framework.cache.redis.AbstractBucketRedisCache;

/**
 * 会员用户信息缓存
 * 
 * @author MiaoQiang
 * @date 2024/10/22.
 */
@Component
public class UserCache extends AbstractBucketRedisCache<UserParam, Object> {

    @Autowired
    public UserCache(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    protected Object getFromDB(UserParam cacheParam) {
        return null;
    }
}
