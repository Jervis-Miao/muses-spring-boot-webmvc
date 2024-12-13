/*
 * Copyright 2024 Jervis All Rights Reserved
 */
package xyz.muses.constants.redis.lock;


import xyz.muses.framework.cache.constant.RedisConst;

/**
 * REDIS业务锁常量
 *
 * @author jervis
 * @date 2024/10/9
 */
public interface RedisLockConst extends RedisConst {

    String LOCK_KEY_PREFIX = SYSTEM_REDIS_PRE_KEY + "LOCK:";

    enum Lock {
        SCRIPT_LOAD_TASK(LOCK_KEY_PREFIX.concat("SCRIPT:LOAD:TASK:"));

        private String key;

        Lock(String key) {
            this.key = key;
        }

        public String key() {
            return key;
        }
    }
}
