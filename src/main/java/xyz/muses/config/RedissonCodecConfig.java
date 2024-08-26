/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.config;

import org.redisson.client.codec.StringCodec;
import org.redisson.codec.FstCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jervis
 * @date 2024/8/26
 */
@Configuration
public class RedissonCodecConfig {

    @Bean("fstCodec")
    public FstCodec fstCodec() {
        return new FstCodec();
    }

    @Bean("jsonJacksonCodec")
    public JsonJacksonCodec jsonJacksonCodec() {
        return new JsonJacksonCodec();
    }

    @Bean("jdkCodec")
    public SerializationCodec jdkCodec() {
        return new SerializationCodec();
    }

    @Bean("StringCodec")
    public StringCodec stringCodec() {
        return new StringCodec();
    }
}
