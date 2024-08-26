/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.utils.protocol.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpStatus;

import xyz.muses.utils.protocol.ProtocolResponse;

/**
 * http协议第三方接口处理结果
 *
 * @author miaoqiang
 * @date 2020/5/7.
 */
public class HttpProtocolResponse extends ProtocolResponse {
    /**
     * HTTP状态代码
     *
     * @see HttpStatus
     */
    private int statusCode = HttpStatus.SC_OK;

    private Map<String, String> headers = new HashMap<>();

    public int getStatusCode() {
        return statusCode;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("状态码：").append(statusCode).append("\r\n");
        if (MapUtils.isNotEmpty(headers)) {
            sb.append("响应头：").append(headers).append("\r\n");
        }
        sb.append(super.toString());
        return sb.toString();
    }
}
