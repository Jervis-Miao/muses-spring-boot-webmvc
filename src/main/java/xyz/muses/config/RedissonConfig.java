/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.config;

import java.util.stream.Collectors;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.FstCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.muses.config.redis.RedissonProperties;
import xyz.muses.config.redis.SentinelProperties;
import xyz.muses.config.redis.SingleProperties;

/**
 * @author jervis
 * @date 2020/12/1.
 */
@Configuration
@EnableConfigurationProperties({SentinelProperties.class, SingleProperties.class, RedissonProperties.class})
public class RedissonConfig {

    @Bean("fstCodec")
    public FstCodec fstCodec() {
        return new FstCodec();
    }

    @Bean("jsonJacksonCodec")
    public JsonJacksonCodec jsonJacksonCodec() {
        return new JsonJacksonCodec();
    }

    @Bean("jdkCodec")
    public SerializationCodec jdkCodec() {
        return new SerializationCodec();
    }

    @Bean("StringCodec")
    public StringCodec stringCodec() {
        return new StringCodec();
    }

    @Bean("sentinelRedissonClient")
    public RedissonClient sentinelRedisson(@Qualifier("fstCodec") Codec codec, SentinelProperties sentinelProp,
        RedissonProperties redissonProp) {
        Config config = new Config();
        config.useSentinelServers()
            .setMasterName(sentinelProp.getMasterName())
            .addSentinelAddress(
                sentinelProp.getServers().stream().map(s -> "redis://" + s.getHost() + ":" + s.getPort())
                    .collect(Collectors.toList()).toArray(new String[sentinelProp.getServers().size()]))
            .setReadMode(sentinelProp.getReadMode())
            .setSubscriptionMode(sentinelProp.getSubscriptionMode())
            .setSlaveConnectionMinimumIdleSize(sentinelProp.getSlaveConnectionMinimumIdleSize())
            .setSlaveConnectionPoolSize(sentinelProp.getSlaveConnectionPoolSize())
            .setMasterConnectionMinimumIdleSize(sentinelProp.getMasterConnectionMinimumIdleSize())
            .setMasterConnectionPoolSize(sentinelProp.getMasterConnectionPoolSize())
            .setFailedSlaveReconnectionInterval(sentinelProp.getFailedSlaveReconnectionInterval())
            .setFailedSlaveCheckInterval(sentinelProp.getFailedSlaveCheckInterval())
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

    @Bean("singleRedissonClient")
    @ConditionalOnMissingBean(name = "sentinelRedissonClient")
    public RedissonClient singleRedisson(@Qualifier("fstCodec") Codec codec, SingleProperties singleProp,
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
