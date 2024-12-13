/*
 * Copyright 2024 Jervis All Rights Reserved
 */
package xyz.muses.cache.aop;

/**
 * 缓存更新处理器
 * 
 * @author jervis
 * @date 2024/11/4
 */
public interface ICacheRefreshProcessor {

    /**
     * 更新缓存
     * 
     * @param args
     * @param param
     * @param index
     */
    void refreshCache(Object[] args, CacheRefresh.Param param, int index);

}
