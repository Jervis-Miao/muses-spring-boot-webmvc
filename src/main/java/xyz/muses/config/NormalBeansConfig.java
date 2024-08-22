/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.muses.framework.common.utils.JsonMapper;
import xyz.muses.framework.common.utils.SpringContextUtils;
import xyz.muses.utils.JwtUserUtils;

/**
 * @author jervis
 * @date 2020/12/1.
 */
@Configuration
public class NormalBeansConfig {

    @Value("${jwt.algorithm.secret}")
    private String secret;
    @Value("${jwt.expires.ttl}")
    private long ttl;

    @Bean("springContextUtils")
    public SpringContextUtils initSpringContextUtils() {
        return new SpringContextUtils();
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @Primary
    public JsonMapper initJsonMapper(ObjectMapper objectMapper) {
        return new JsonMapper(objectMapper);
    }

    @Bean
    public JwtUserUtils initJwtUtils(JsonMapper jsonMapper) {
        return new JwtUserUtils(this.secret, this.ttl, jsonMapper);
    }
}
