/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.muses.config.orika.MusesMapperFactoryConfigurer;
import xyz.muses.config.orika.MusesOrikaMapperFactoryBuilderConfigurer;

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
