/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jervis
 * @date 2024/8/26
 */
@ConfigurationProperties(prefix = "redis.single")
public class SingleProperties {

    private String host;
    private Integer port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
