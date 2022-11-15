/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config.webmvc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import cn.muses.web.model.dto.ApiBaseResponseDTO;

/**
 * @author jervis
 * @date 2020/12/3.
 */
@ControllerAdvice
public class ApiResponseBody implements ResponseBodyAdvice<ApiBaseResponseDTO> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        final Type genericParameterType = methodParameter.getGenericParameterType();
        if (genericParameterType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)genericParameterType;

            return isSame(parameterizedType.getRawType(), ApiBaseResponseDTO.class);
        }

        return false;
    }

    private boolean isSame(Type type, Class clazz) {
        if (type instanceof Class) {
            return type.getTypeName().equals(clazz.getName());
        } else if (type instanceof ParameterizedType) {
            return ((ParameterizedType)type).getRawType().getTypeName().equals(clazz.getName());
        }

        return false;
    }

    @Override
    public ApiBaseResponseDTO beforeBodyWrite(ApiBaseResponseDTO baseResponseDTO, MethodParameter methodParameter,
        MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
        ServerHttpResponse serverHttpResponse) {
        baseResponseDTO.setSignature("123123");

        return baseResponseDTO;
    }
}
