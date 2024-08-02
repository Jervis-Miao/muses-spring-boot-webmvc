/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.muses.utils.JsonMapper;
import xyz.muses.utils.SpringContextUtils;

/**
 * @author jervis
 * @date 2020/12/1.
 */
@Configuration
public class NormalBeansConfig {

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
}
