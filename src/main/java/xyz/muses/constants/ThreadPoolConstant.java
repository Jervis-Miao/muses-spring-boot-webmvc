package xyz.muses.constants;

import org.springframework.scheduling.annotation.AnnotationAsyncExecutionInterceptor;

/**
 * 线程池配置
 *
 * @author shijianpeng
 */
public interface ThreadPoolConstant {

    /**
     * 线程池信息
     */
    enum ThreadPoolInfo {
        /**
         * 默认线程池
         */
        DEFAULT_TASK_EXECUTOR(AnnotationAsyncExecutionInterceptor.DEFAULT_TASK_EXECUTOR_BEAN_NAME, "Async.Thread-",
            "Async"),

        // 缓存刷新线程池
        REFRESH_CACHE_EXECUTOR("refreshCacheExecutor", "RefreshCache.Thread-", "RefreshCache"),

        // 定时任务线程池
        SCHEDULED_TASK_EXECUTOR("scheduledTaskExecutor", "ScheduledTask.Thread-", "ScheduledTask");

        /**
         * BeanName
         */
        private final String beanName;
        /**
         * 线程名前缀
         */
        private final String threadNamePrefix;
        /**
         * 线程组名
         */
        private final String threadGroupName;

        ThreadPoolInfo(String beanName, String threadNamePrefix, String threadGroupName) {
            this.beanName = beanName;
            this.threadNamePrefix = threadNamePrefix;
            this.threadGroupName = threadGroupName;
        }

        public String getBeanName() {
            return beanName;
        }

        public String getThreadNamePrefix() {
            return threadNamePrefix;
        }

        public String getThreadGroupName() {
            return threadGroupName;
        }
    }
}
