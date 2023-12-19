/*
 * Copyright 2021 XYZ Co., Ltd. All Rights Reserved
 */
package cn.muses.config;

import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.xyz.chaos.orm.mybatis.easylist.paginator.OffsetLimitInterceptor;

/**
 * @author miaoqiang
 * @date 2023/12/19.
 */
@Configuration
public class MybatisConfig {

    @Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            configuration.setCacheEnabled(false);
            configuration.setLazyLoadingEnabled(false);
            configuration.setAggressiveLazyLoading(false);
            configuration.setMultipleResultSetsEnabled(true);
            configuration.setUseColumnLabel(true);
            configuration.setUseGeneratedKeys(false);
            configuration.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
            configuration.setDefaultExecutorType(ExecutorType.REUSE);
            configuration.setDefaultStatementTimeout(30);
            configuration.setSafeRowBoundsEnabled(false);
            configuration.setMapUnderscoreToCamelCase(false);
            configuration.setLocalCacheScope(LocalCacheScope.SESSION);
            configuration.setJdbcTypeForNull(JdbcType.OTHER);
            configuration.setCallSettersOnNulls(false);
            configuration.setLogImpl(Slf4jImpl.class);
            configuration.setProxyFactory(new CglibProxyFactory());
        };
    }

    @Bean
    OffsetLimitInterceptor mybatisOffsetLimitInterceptor() {
        OffsetLimitInterceptor interceptor = new OffsetLimitInterceptor();
        interceptor.setDialectClass("cn.xyz.chaos.orm.mybatis.easylist.paginator.dialect.MySQLDialect");
        return interceptor;
    }
}
