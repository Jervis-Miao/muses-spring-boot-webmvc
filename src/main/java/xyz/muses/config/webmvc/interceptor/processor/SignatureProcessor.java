/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.config.webmvc.interceptor.processor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.muses.config.webmvc.interceptor.IApiInterceptorProcessor;
import xyz.muses.utils.JsonMapper;
import xyz.muses.utils.SignatureUtils;

/**
 * @author jervis
 * @date 2020/12/3.
 */
@Component
public class SignatureProcessor implements IApiInterceptorProcessor {
    private static final String SIGNATURE_HEADER_NAME = "signature";

    @Autowired
    private JsonMapper jsonMapper;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, String requestBody, HttpServletResponse response,
        Object handler) throws Exception {
        if (request.getMethod().equals(RequestMethod.GET.name())) {
            return true;
        }

        final Map<String, Object> map = jsonMapper.fromJson(requestBody, Map.class);
        final String sign = String.valueOf(map.remove(SIGNATURE_HEADER_NAME));

        return SignatureUtils.verify(jsonMapper.toJson(map), sign, "");
    }
}
