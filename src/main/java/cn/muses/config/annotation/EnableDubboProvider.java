/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

import cn.muses.config.dubbo.ProviderConfig;

/**
 * @author jervis
 * @date 2020/3/20.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableDubboConfiguration
@Import({ProviderConfig.class})
public @interface EnableDubboProvider {}
