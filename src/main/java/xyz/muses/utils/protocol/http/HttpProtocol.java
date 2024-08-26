/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.utils.protocol.http;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.client.methods.HttpGet;

import xyz.muses.utils.protocol.Protocol;
import xyz.muses.utils.protocol.constant.HttpProtocolConst;

/**
 * @author miaoqiang
 * @date 2019/7/12.
 */
public class HttpProtocol extends Protocol {
    private static final long serialVersionUID = 8245034198381114704L;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求首部字段：
     *
     * @see org.apache.http.HttpHeaders
     */
    private List<HttpHeader> reqHeaders;

    /**
     * 媒体格式
     *
     * @see HttpProtocolConst.CONTENT_TYPE#contentType
     */
    private String contentType;

    /**
     * 编码格式
     */
    private String charset;

    /**
     * SSL证书，可为空
     */
    private HttpSsl ssl;

    public HttpProtocol() {
        super(HttpProtocolConst.SO_TIMEOUT, HttpProtocolConst.CONNECTION_TIMEOUT);
        this.requestMethod = HttpGet.METHOD_NAME;
        this.contentType = HttpProtocolConst.CONTENT_TYPE.TEXT_HTML.getContentType();
        this.charset = HttpProtocolConst.DEFAULT_ENCODE;
    }

    public HttpProtocol(String url, String userName, String password, Boolean downloadFlag, Integer socketTimeOut,
        Integer connTimeOut, String proxyAddress, Integer proxyPort, String proxyUserName, String proxyPassword,
        String requestMethod, List<HttpHeader> reqHeaders, String contentType, String charset, HttpSsl ssl) {
        super(url, userName, password, downloadFlag, socketTimeOut, connTimeOut, proxyAddress, proxyPort, proxyUserName,
            proxyPassword);
        this.requestMethod = requestMethod;
        this.reqHeaders = reqHeaders;
        this.contentType = contentType;
        this.charset = charset;
        this.ssl = ssl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public List<HttpHeader> getReqHeaders() {
        return reqHeaders;
    }

    public void setReqHeaders(List<HttpHeader> reqHeaders) {
        this.reqHeaders = reqHeaders;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public HttpSsl getSsl() {
        return ssl;
    }

    public void setSsl(HttpSsl ssl) {
        this.ssl = ssl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("requestMethod", requestMethod)
            .append("contentType", contentType)
            .append("charset", charset)
            .appendSuper(super.toString())
            .toString();
    }
}
