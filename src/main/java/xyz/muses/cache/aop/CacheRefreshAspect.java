/*
 * Copyright 2024 Jervis All Rights Reserved
 */
package xyz.muses.cache.aop;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author jervis
 * @date 2024/11/4
 */
@Aspect
@Component
public class CacheRefreshAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Map<String, ICacheRefreshProcessor> processors;

    /**
     * 在标记了CacheRefresh同步刷新注解的方法上，进行后置增强，进行缓存刷新
     *
     * @param joinPoint
     */
    @Async("refreshCacheExecutor")
    @AfterReturning(value = "@annotation(xyz.muses.cache.aop.CacheRefresh)")
    public void doAfter(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            Signature signature = joinPoint.getSignature();
            String name = new StringBuilder(signature.getDeclaringTypeName()).append(".").append(signature.getName())
                .toString();
            logger.info("方法: " + name + ", 触发了缓存刷新");

            MethodSignature methodSignature = (MethodSignature)signature;
            CacheRefresh syncRefresh = methodSignature.getMethod().getAnnotation(CacheRefresh.class);
            CacheRefresh.Cache[] caches = syncRefresh.caches();
            int index = syncRefresh.paramIndex();
            CacheRefresh.Param param = syncRefresh.param();

            // 更新缓存
            if (ArrayUtils.isNotEmpty(caches)) {
                for (CacheRefresh.Cache cache : caches) {
                    processors.get(cache.name()).refreshCache(args, param, index);
                }
            }
        } catch (Exception e) {
            logger.error("缓存刷新异常", e);
        }
    }
}
