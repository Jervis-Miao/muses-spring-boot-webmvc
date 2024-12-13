package xyz.muses.constants.redis.cache;

import java.util.concurrent.TimeUnit;

import xyz.muses.framework.cache.constant.ICacheEnum;
import xyz.muses.framework.cache.constant.RedisConst;

/**
 * 缓存常量Key
 *
 * @author jervis
 */
public interface RedisCacheConst extends RedisConst {

    /**
     * 缓存Key的前缀
     */
    String KEY_PREFIX = RedisConst.SYSTEM_REDIS_PRE_KEY;

    /**
     * 缓存常量Key
     */
    enum Key implements ICacheEnum {
        JWT(KEY_PREFIX.concat("USER:JWT"), 30L, TimeUnit.DAYS),
        USER(KEY_PREFIX.concat("USER:"), 30L, TimeUnit.DAYS);

        /** Key的值 **/
        private final String code;

        /** 有效期 **/
        private final Long timeToLive;

        /** 有效期的单位 **/
        private final TimeUnit timeUnit;

        Key(String code, Long timeToLive, TimeUnit timeUnit) {
            this.code = code;
            this.timeToLive = timeToLive;
            this.timeUnit = timeUnit;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public Long getTimeToLive() {
            return timeToLive;
        }

        @Override
        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public long toMillis() {
            if (null == this.timeUnit || null == this.timeToLive) {
                return -1;
            }

            return this.timeUnit.toMillis(this.timeToLive);
        }
    }
}
