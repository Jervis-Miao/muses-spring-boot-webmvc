/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.alicp.jetcache.embedded.CaffeineCacheBuilder;
import com.alicp.jetcache.embedded.EmbeddedCacheBuilder;
import com.alicp.jetcache.redis.RedisCacheBuilder;
import com.alicp.jetcache.support.FastjsonKeyConvertor;
import com.alicp.jetcache.support.KryoValueDecoder;
import com.alicp.jetcache.support.KryoValueEncoder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;
import xyz.muses.config.redis.RedissonProperties;
import xyz.muses.config.redis.SentinelProperties;
import xyz.muses.config.redis.SingleProperties;
import xyz.muses.framework.cache.constant.RedisConst;

/**
 * @author jervis
 * @date 2020/12/1.
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "cn.muses")
@EnableConfigurationProperties({SentinelProperties.class, SingleProperties.class, RedissonProperties.class})
public class JetCacheConfig {

    @Bean
    @ConditionalOnBean(name = "sentinelRedissonClient")
    public Pool<Jedis> sentinelPool(SentinelProperties sentinelProp, RedissonProperties redissonProp) {
        return new JedisSentinelPool(
            sentinelProp.getMasterName(), new HashSet<>(sentinelProp.getServers().stream()
                .map(s -> s.getHost() + ":" + s.getPort()).collect(Collectors.toList())),
            new JedisPoolConfig(), redissonProp.getConnectTimeout(), redissonProp.getPassword(),
            redissonProp.getDatabase());
    }

    @Bean
    @ConditionalOnBean(name = "singleRedissonClient")
    public Pool<Jedis> singlePool(SingleProperties singleProp, RedissonProperties redissonProp) {
        return new JedisPool(new JedisPoolConfig(), singleProp.getHost(), singleProp.getPort(),
            redissonProp.getConnectTimeout(), redissonProp.getPassword(), redissonProp.getDatabase());
    }

    @Bean
    public SpringConfigProvider springConfigProvider() {
        return new SpringConfigProvider();
    }

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public GlobalCacheConfig config(SpringConfigProvider configProvider, Pool<Jedis> pool) {
        Map<String, CacheBuilder> localBuilders = new HashMap<>(1);
        // 本地缓存配置
        EmbeddedCacheBuilder localBuilder =
            CaffeineCacheBuilder.createCaffeineCacheBuilder().keyConvertor(FastjsonKeyConvertor.INSTANCE);
        localBuilder.setLimit(100);
        localBuilder.setCacheNullValue(false);
        localBuilder.setExpireAfterAccessInMillis(60 * 1000);
        localBuilders.put(CacheConsts.DEFAULT_AREA, localBuilder);
        // redis分布式缓存配置
        Map<String, CacheBuilder> remoteBuilders = new HashMap<>(1);
        RedisCacheBuilder remoteCacheBuilder =
            RedisCacheBuilder.createRedisCacheBuilder().keyConvertor(FastjsonKeyConvertor.INSTANCE)
                .valueEncoder(KryoValueEncoder.INSTANCE).valueDecoder(KryoValueDecoder.INSTANCE).jedisPool(pool);
        remoteCacheBuilder.setCacheNullValue(true);
        remoteCacheBuilder.setExpireAfterWriteInMillis(4 * 60 * 60 * 1000);
        remoteCacheBuilder.setKeyPrefix(RedisConst.SYSTEM_REDIS_PRE_KEY);
        remoteBuilders.put(CacheConsts.DEFAULT_AREA, remoteCacheBuilder);
        // 全局配置
        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        globalCacheConfig.setConfigProvider(configProvider);
        globalCacheConfig.setLocalCacheBuilders(localBuilders);
        globalCacheConfig.setRemoteCacheBuilders(remoteBuilders);
        globalCacheConfig.setStatIntervalMinutes(15);
        globalCacheConfig.setAreaInCacheName(false);
        globalCacheConfig.setHiddenPackages(new String[] {""});
        return globalCacheConfig;
    }
}
