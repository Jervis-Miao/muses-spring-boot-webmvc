/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import xyz.muses.config.redis.SingleProperties;
import xyz.muses.config.redis.RedissonProperties;

/**
 * @author jervis
 * @date 2024/8/26
 */
// @Configuration
@EnableConfigurationProperties({SingleProperties.class, RedissonProperties.class})
public class RedissonSingleConfig {

    @Bean("redissonClient")
    public RedissonClient initRedisson(@Qualifier("fstCodec") Codec codec, SingleProperties singleProp,
        RedissonProperties redissonProp) {
        Config config = new Config();
        config.useSingleServer()
            .setAddress("redis://" + singleProp.getHost() + ":" + singleProp.getPort())
            .setIdleConnectionTimeout(redissonProp.getIdleConnectionTimeout())
            .setConnectTimeout(redissonProp.getConnectTimeout())
            .setTimeout(redissonProp.getTimeout())
            .setRetryAttempts(redissonProp.getRetryAttempts())
            .setRetryInterval(redissonProp.getRetryInterval())
            .setSubscriptionConnectionMinimumIdleSize(redissonProp.getSubscriptionConnectionMinimumIdleSize())
            .setSubscriptionConnectionPoolSize(redissonProp.getSubscriptionConnectionPoolSize())
            .setSubscriptionsPerConnection(redissonProp.getSubscriptionsPerConnection())
            .setDatabase(redissonProp.getDatabase())
            .setPassword(redissonProp.getPassword());
        // 序列化方式
        config.setCodec(codec);
        return Redisson.create(config);
    }
}
