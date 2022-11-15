package cn.muses;

/**
 * Copyright 2022 All rights reserved.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import cn.muses.config.annotation.EnableDubboProvider;

/**
 * @author jervis
 * @date 2022/11/14.
 */
@SpringBootApplication
// @EnableScheduling
// @EnableAspectJAutoProxy(exposeProxy = true)
// @EnableDubboProvider
public class MusesApplication {

    /**
     * 启动
     *
     * @param args
     */
    public static void main(String[] args) {
        loadConfiguration();
        SpringApplication.run(MusesApplication.class, args);
    }

    /**
     * 加载配置
     */
    private static final void loadConfiguration() {
        System.setProperty("test", "test");
    }
}
