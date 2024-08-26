/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.utils.protocol;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author miaoqiang
 * @date 2020/4/26.
 */
public class Protocol implements Serializable {
    private static final long serialVersionUID = 6254388890526032008L;

    /**
     * url地址
     */
    private String url;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 下载标记
     */
    private Boolean downloadFlag;

    /**
     * 读数据超时时间(单位毫秒)
     */
    private Integer socketTimeOut;

    /**
     * 连接超时时间(单位毫秒)
     */
    private Integer connTimeOut;

    /**
     * 代理地址
     */
    private String proxyAddress;

    /**
     * 代理端口
     */
    private Integer proxyPort;

    /**
     * 代理用户名
     */
    private String proxyUserName;

    /**
     * 代理密码
     */
    private String proxyPassword;

    public Protocol() {
        this(null, null);
    }

    public Protocol(Integer socketTimeOut, Integer connTimeOut) {
        this(null, null, null, Boolean.FALSE, socketTimeOut, connTimeOut, null, null, null, null);
    }

    public Protocol(String url, String userName, String password, Boolean downloadFlag, Integer socketTimeOut,
        Integer connTimeOut, String proxyAddress, Integer proxyPort, String proxyUserName, String proxyPassword) {
        super();
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.downloadFlag = downloadFlag;
        this.socketTimeOut = socketTimeOut;
        this.connTimeOut = connTimeOut;
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
        this.proxyUserName = proxyUserName;
        this.proxyPassword = proxyPassword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDownloadFlag() {
        return downloadFlag;
    }

    public void setDownloadFlag(Boolean downloadFlag) {
        this.downloadFlag = downloadFlag;
    }

    public Integer getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(Integer socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public Integer getConnTimeOut() {
        return connTimeOut;
    }

    public void setConnTimeOut(Integer connTimeOut) {
        this.connTimeOut = connTimeOut;
    }

    public String getProxyAddress() {
        return proxyAddress;
    }

    public void setProxyAddress(String proxyAddress) {
        this.proxyAddress = proxyAddress;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUserName() {
        return proxyUserName;
    }

    public void setProxyUserName(String proxyUserName) {
        this.proxyUserName = proxyUserName;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("...")
            .toString();
    }
}
