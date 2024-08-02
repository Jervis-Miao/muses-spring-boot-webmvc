/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.web.model.dto;

import java.util.StringJoiner;

/**
 * @author jervis
 * @date 2020/12/3.
 */
public class ApiBaseResponseDTO<T> extends BaseResponseDTO<T> {
    private static final long serialVersionUID = 9018073932146610258L;

    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiBaseResponseDTO.class.getSimpleName() + "[", "]")
            .add("signature='" + signature + "'")
            .toString();
    }
}
