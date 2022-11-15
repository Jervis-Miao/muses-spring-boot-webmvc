package cn.muses.constants;

import java.util.concurrent.TimeUnit;

/**
 * 缓存常量Key
 *
 * @author shijianpeng
 */
public interface CacheConstant extends RedisConstant {

    /**
     * 缓存Key的前缀
     */
    String KEY_PREFIX = RedisConstant.MUSES_REDIS_PRE_KEY;

    /**
     * 缓存常量Key
     */
    enum Key {
        GLOBAL_TOKEN(KEY_PREFIX.concat("global:token:"), 30, TimeUnit.DAYS);

        /**
         * Key的值
         */
        private final String value;
        /**
         * 有效期
         */
        private Integer expires;
        /**
         * 有效期的单位
         */
        private TimeUnit timeUnit;

        Key(String value) {
            this.value = value;
        }

        Key(String value, Integer expires, TimeUnit timeUnit) {
            this.value = value;
            this.expires = expires;
            this.timeUnit = timeUnit;
        }

        public String getValue() {
            return value;
        }

        public Integer getExpires() {
            return expires;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public String cacheKey(String data) {
            return new StringBuilder(this.value).append(data).toString();
        }

        public long toMillis() {
            if (null == this.timeUnit || null == this.expires) {
                return -1;
            }

            return this.timeUnit.toMillis(this.expires.longValue());
        }
    }
}
