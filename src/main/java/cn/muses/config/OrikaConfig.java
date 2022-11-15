/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.muses.config.orika.MusesMapperFactoryConfigurer;
import cn.muses.config.orika.MusesOrikaMapperFactoryBuilderConfigurer;

/**
 * @author jervis
 * @date 2020/12/1.
 */
@Configuration
public class OrikaConfig {

    @Bean
    public MusesMapperFactoryConfigurer orpheusMapperFactoryConfigurer() {
        return new MusesMapperFactoryConfigurer();
    }

    @Bean
    public MusesOrikaMapperFactoryBuilderConfigurer xyzOrikaMapperFactoryBuilderConfigurer() {
        return new MusesOrikaMapperFactoryBuilderConfigurer();
    }
}
