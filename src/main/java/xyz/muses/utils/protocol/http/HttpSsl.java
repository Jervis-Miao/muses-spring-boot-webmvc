/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.utils.protocol.http;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author miaoqiang
 * @date 2020/4/26.
 */
public class HttpSsl implements Serializable {
    private static final long serialVersionUID = 9056897343573475907L;

    /**
     * keystore 文件路径，必填
     */
    private String keyPath;

    /**
     * keystore 密码，必填
     */
    private String keyPwd;

    /**
     * truststore 文件路径，必填
     */
    private String trustPath;

    /**
     * truststore 密码，必填
     */
    private String trustPwd;

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getKeyPwd() {
        return keyPwd;
    }

    public void setKeyPwd(String keyPwd) {
        this.keyPwd = keyPwd;
    }

    public String getTrustPath() {
        return trustPath;
    }

    public void setTrustPath(String trustPath) {
        this.trustPath = trustPath;
    }

    public String getTrustPwd() {
        return trustPwd;
    }

    public void setTrustPwd(String trustPwd) {
        this.trustPwd = trustPwd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("...")
            .toString();
    }
}
