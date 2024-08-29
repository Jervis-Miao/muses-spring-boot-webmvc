/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.config.redis;

import java.util.List;

import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;
import org.redisson.connection.balancer.LoadBalancer;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jervis
 * @date 2024/8/26
 */
@ConfigurationProperties(prefix = "redis.sentinel")
public class SentinelProperties {

    private String masterName;
    private List<Server> servers;
    private ReadMode readMode;
    private SubscriptionMode subscriptionMode;
    private int slaveConnectionMinimumIdleSize;
    private int slaveConnectionPoolSize;
    private int masterConnectionMinimumIdleSize;
    private int masterConnectionPoolSize;
    private int failedSlaveReconnectionInterval;
    private int failedSlaveCheckInterval;
    private int pingConnectionInterval;
    private boolean keepAlive;
    private boolean tcpNoDelay;
    private String loadBalancer;

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public ReadMode getReadMode() {
        return readMode;
    }

    public void setReadMode(ReadMode readMode) {
        this.readMode = readMode;
    }

    public SubscriptionMode getSubscriptionMode() {
        return subscriptionMode;
    }

    public void setSubscriptionMode(SubscriptionMode subscriptionMode) {
        this.subscriptionMode = subscriptionMode;
    }

    public int getSlaveConnectionMinimumIdleSize() {
        return slaveConnectionMinimumIdleSize;
    }

    public void setSlaveConnectionMinimumIdleSize(int slaveConnectionMinimumIdleSize) {
        this.slaveConnectionMinimumIdleSize = slaveConnectionMinimumIdleSize;
    }

    public int getSlaveConnectionPoolSize() {
        return slaveConnectionPoolSize;
    }

    public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
        this.slaveConnectionPoolSize = slaveConnectionPoolSize;
    }

    public int getMasterConnectionMinimumIdleSize() {
        return masterConnectionMinimumIdleSize;
    }

    public void setMasterConnectionMinimumIdleSize(int masterConnectionMinimumIdleSize) {
        this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
    }

    public int getMasterConnectionPoolSize() {
        return masterConnectionPoolSize;
    }

    public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
        this.masterConnectionPoolSize = masterConnectionPoolSize;
    }

    public int getFailedSlaveReconnectionInterval() {
        return failedSlaveReconnectionInterval;
    }

    public void setFailedSlaveReconnectionInterval(int failedSlaveReconnectionInterval) {
        this.failedSlaveReconnectionInterval = failedSlaveReconnectionInterval;
    }

    public int getFailedSlaveCheckInterval() {
        return failedSlaveCheckInterval;
    }

    public void setFailedSlaveCheckInterval(int failedSlaveCheckInterval) {
        this.failedSlaveCheckInterval = failedSlaveCheckInterval;
    }

    public int getPingConnectionInterval() {
        return pingConnectionInterval;
    }

    public void setPingConnectionInterval(int pingConnectionInterval) {
        this.pingConnectionInterval = pingConnectionInterval;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public String getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(String loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public static class Server {
        private String host;
        private String port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }
    }
}
