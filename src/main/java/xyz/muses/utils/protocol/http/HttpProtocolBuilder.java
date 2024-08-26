/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.utils.protocol.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.client.methods.HttpGet;

import xyz.muses.utils.protocol.constant.HttpProtocolConst;

/**
 * @author jervis
 * @date 2024/8/23
 */
public class HttpProtocolBuilder {

    private String requestMethod;
    private List<HttpHeader> reqHeaders;
    private String contentType;
    private String charset;
    private HttpSsl ssl;

    private String url;
    private String userName;
    private String password;
    private Boolean downloadFlag;
    private Integer socketTimeOut;
    private Integer connTimeOut;
    private String proxyAddress;
    private Integer proxyPort;
    private String proxyUserName;
    private String proxyPassword;

    public static HttpProtocolBuilder create() {
        HttpProtocolBuilder builder = new HttpProtocolBuilder();
        builder.setSocketTimeOut(HttpProtocolConst.SO_TIMEOUT);
        builder.setConnTimeOut(HttpProtocolConst.CONNECTION_TIMEOUT);
        builder.setDownloadFlag(Boolean.FALSE);
        builder.setRequestMethod(HttpGet.METHOD_NAME);
        builder.setContentType(HttpProtocolConst.CONTENT_TYPE.TEXT_HTML.getContentType());
        builder.setCharset(HttpProtocolConst.DEFAULT_ENCODE);
        return builder;
    }

    public HttpProtocolBuilder setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public HttpProtocolBuilder addReqHeader(HttpHeader... reqHeaders) {
        if (this.reqHeaders == null) {
            this.reqHeaders = new ArrayList<>();
        }
        if (ArrayUtils.isNotEmpty(reqHeaders)) {
            this.reqHeaders.addAll(Arrays.asList(reqHeaders));
        }
        return this;
    }

    public HttpProtocolBuilder setReqHeaders(List<HttpHeader> reqHeaders) {
        this.reqHeaders = reqHeaders;
        return this;
    }

    public HttpProtocolBuilder setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public HttpProtocolBuilder setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public HttpProtocolBuilder setSsl(HttpSsl ssl) {
        this.ssl = ssl;
        return this;
    }

    public HttpProtocolBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpProtocolBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public HttpProtocolBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public HttpProtocolBuilder setDownloadFlag(Boolean downloadFlag) {
        this.downloadFlag = downloadFlag;
        return this;
    }

    public HttpProtocolBuilder setSocketTimeOut(Integer socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
        return this;
    }

    public HttpProtocolBuilder setConnTimeOut(Integer connTimeOut) {
        this.connTimeOut = connTimeOut;
        return this;
    }

    public HttpProtocolBuilder setProxyAddress(String proxyAddress) {
        this.proxyAddress = proxyAddress;
        return this;
    }

    public HttpProtocolBuilder setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
        return this;
    }

    public HttpProtocolBuilder setProxyUserName(String proxyUserName) {
        this.proxyUserName = proxyUserName;
        return this;
    }

    public HttpProtocolBuilder setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
        return this;
    }

    public HttpProtocol build() {
        return new HttpProtocol(this.url, this.userName, this.password, this.downloadFlag, this.socketTimeOut,
            this.connTimeOut, this.proxyAddress, this.proxyPort, this.proxyUserName, this.proxyPassword,
            this.requestMethod, this.reqHeaders, this.contentType, this.charset, this.ssl);
    }
}
