/*
 * Copyright 2024 Jervis All Rights Reserved
 */
package xyz.muses.cache.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.muses.exceptions.MusesException;

/**
 * @author jervis
 * @date 2024/11/4
 */
public abstract class AbstractCacheRefreshProcessor<T> implements ICacheRefreshProcessor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public final void refreshCache(Object[] args, CacheRefresh.Param param, int index) {
        // 懒加载缓存则直接清除，等待下次使用时再加载
        if (this.isLazy()) {
            this.clear(this.readKeyFromArgs(param, args, index));
        } else {
            this.refresh(this.readKeyFromArgs(param, args, index));
        }
    }

    /**
     * 是否懒加载缓存
     * 
     * @return
     */
    public boolean isLazy() {
        return false;
    }

    /**
     * 清除缓存
     * 
     * @param key
     */
    protected void clear(T key) {
        String error = "Can not clear cache by class: " + this.getClass().getName();
        logger.error(error);
        throw new MusesException(error);
    }

    /**
     * 刷新缓存
     * 
     * @param key
     */
    protected void refresh(T key) {
        String error = "Can not refresh cache by class: " + this.getClass().getName();
        logger.error(error);
        throw new MusesException(error);
    }

    /**
     * 从参数中读取主键
     * 
     * @param param
     * @param args
     * @param index
     * @return
     */
    protected abstract T readKeyFromArgs(CacheRefresh.Param param, Object[] args, int index);
}
