/*
 * Copyright 2021 XYZ Co., Ltd. All Rights Reserved
 */
package xyz.muses.config;

import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.muses.framework.orm.mybatis.CybersecTypeHandler;
import xyz.muses.framework.orm.mybatis.MyBatisRepository;
import xyz.muses.framework.orm.mybatis.cybersec.ParameterCybersecInterceptor;
import xyz.muses.framework.orm.mybatis.cybersec.ResultSetCybersecInterceptor;
import xyz.muses.framework.orm.mybatis.easylist.paginator.OffsetLimitInterceptor;

/**
 * @author miaoqiang
 * @date 2023/12/19.
 */
@Configuration
@MapperScan(basePackages = "xyz.muses.config.repository", annotationClass = MyBatisRepository.class)
public class MybatisConfig {

    @Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            // 启用缓存
            configuration.setCacheEnabled(false);
            // 延迟加载
            configuration.setLazyLoadingEnabled(false);
            // 当启用时, 有延迟加载属性的对象 在被任意延迟加载的属性被访问时将会完全加载所有属性。否则, 每种属性将会按需要加载
            configuration.setAggressiveLazyLoading(false);
            // 是否允许从一条语句中返回多结果集(需要适合的驱动)
            configuration.setMultipleResultSetsEnabled(true);
            // 使用column label 代替 column name ，依赖于驱动实现。请参阅驱动文档或测试不同模式的结果
            configuration.setUseColumnLabel(true);
            configuration.setUseGeneratedKeys(false);
            configuration.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
            configuration.setDefaultExecutorType(ExecutorType.REUSE);
            // 默认语句执行超时时间
            configuration.setDefaultStatementTimeout(30);
            configuration.setSafeRowBoundsEnabled(false);
            configuration.setMapUnderscoreToCamelCase(false);
            configuration.setLocalCacheScope(LocalCacheScope.SESSION);
            configuration.setJdbcTypeForNull(JdbcType.OTHER);
            configuration.setCallSettersOnNulls(false);
            configuration.setLogImpl(Slf4jImpl.class);
            // CGLIB | JAVASSIST
            configuration.setProxyFactory(new CglibProxyFactory());
            // 别名
            configuration.getTypeAliasRegistry().registerAlias("cyberset", CybersecTypeHandler.class);
        };
    }

    @Bean
    OffsetLimitInterceptor mybatisOffsetLimitInterceptor() {
        OffsetLimitInterceptor interceptor = new OffsetLimitInterceptor();
        interceptor.setDialectClass("xyz.muses.framework.orm.mybatis.easylist.paginator.dialect.MySQLDialect");
        return interceptor;
    }

    @Bean
    ParameterCybersecInterceptor parameterCybersecInterceptor() {
        return new ParameterCybersecInterceptor();
    }

    @Bean
    ResultSetCybersecInterceptor resultSetCybersecInterceptor() {
        return new ResultSetCybersecInterceptor();
    }
}
