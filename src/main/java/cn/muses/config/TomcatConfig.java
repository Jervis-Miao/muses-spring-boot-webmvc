/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.Servlet;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.UpgradeProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author miaoqiang
 * @date 2020/6/8.
 */
@Configuration
@ConditionalOnClass({Servlet.class, Tomcat.class, UpgradeProtocol.class})
public class TomcatConfig implements ApplicationListener<ContextClosedEvent> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 等待时间
     */
    private static final int WAIT_TIME = 60 * 1000;

    /**
     * tomcat连接器列表
     */
    private static volatile List<Connector> connectors = new ArrayList<>(2);

    /**
     * http端口
     */
    @Value("${server.http.port:0}")
    private int httpPort;

    /**
     * 获取tomcat连接器, 重写springboot嵌入的tomcat<br/>
     * {@link org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryConfiguration.EmbeddedTomcat}
     *
     * @return
     */
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        // tomcat服务
        TomcatServletWebServerFactory tomcat = this.createStandardTomcatWebServer();

        // 获取自带连接器
        tomcat.addConnectorCustomizers(connect -> {
            connectors.add(connect);
        });

        // 添加额外tomcat连接器
        if (this.httpPort > 0) {
            List<Connector> additionalTomcatConnectors = this.createStandardAdditionalTomcatConnectors();
            connectors.addAll(additionalTomcatConnectors);
            tomcat.addAdditionalTomcatConnectors(
                additionalTomcatConnectors.toArray(new Connector[additionalTomcatConnectors.size()]));
        }

        return tomcat;
    }

    /**
     * 创建TomcatWebServer
     *
     * @return
     */
    private TomcatServletWebServerFactory createStandardTomcatWebServer() {
        return new TomcatServletWebServerFactory();
    }

    /**
     * 创建额外TomcatConnectors
     *
     * @return
     */
    private List<Connector> createStandardAdditionalTomcatConnectors() {
        List<Connector> connectors = new ArrayList<>(2);
        String protocol = "HTTP/1.1";
        Connector connector = new Connector(protocol);
        connector.setPort(this.httpPort);
        connectors.add(connector);
        return connectors;
    }

    /**
     * 监听停机事件
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        connectors.stream().forEach(connector -> this.gracefulShutdownTomcat(connector));
    }

    /**
     * 优雅停机
     *
     * @param connector
     */
    private void gracefulShutdownTomcat(Connector connector) {
        long startTime = System.currentTimeMillis();
        // tomcat暂停对外服务
        connector.pause();
        // 获取tomcat线程池
        Executor executor = connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            try {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)executor;
                // 线程池优雅停止(不接收新的请求，等待任务运行完成后关闭线程池)
                threadPoolExecutor.shutdown();
                // 线程池堵塞等待一定时间，指定时间内关闭成功则返回true，解除堵塞；否则fasle
                if (threadPoolExecutor.awaitTermination(WAIT_TIME, TimeUnit.MILLISECONDS)) {
                    logger.info("Tomcat thread pool: {} closed, spend time: {}ms", threadPoolExecutor,
                        System.currentTimeMillis() - startTime);
                } else {
                    logger.info("Tomcat thread pool: {} forceClosed, spend time: {}ms", threadPoolExecutor,
                        System.currentTimeMillis() - startTime);
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
