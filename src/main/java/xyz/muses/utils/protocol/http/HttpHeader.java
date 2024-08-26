/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.utils.protocol.http;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author miaoqiang
 * @date 2019/12/30.
 */
public class HttpHeader implements Serializable {
    private static final long serialVersionUID = 9059806953531479526L;

    private String name;
    private String value;

    public HttpHeader() {}

    public HttpHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("name", name)
            .append("value", value)
            .toString();
    }
}
