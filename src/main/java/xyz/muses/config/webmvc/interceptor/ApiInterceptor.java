/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.config.webmvc.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;

import xyz.muses.config.webmvc.BodyReaderHttpServletRequestWrapper;

/**
 * @author jervis
 * @date 2020/12/2.
 */
public class ApiInterceptor extends AbstractInterceptor {

    private List<IApiInterceptorProcessor> processors;

    public ApiInterceptor(List<IApiInterceptorProcessor> processors) {
        super();
        this.processors = processors;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        if (CollectionUtils.isNotEmpty(this.processors)) {
            for (IApiInterceptorProcessor processor : this.processors) {
                if (!processor.preHandle(request, ((BodyReaderHttpServletRequestWrapper)request).getBody(), response,
                    handler)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        @Nullable ModelAndView modelAndView) throws Exception {
        if (CollectionUtils.isNotEmpty(this.processors)) {
            for (IApiInterceptorProcessor processor : this.processors) {
                processor.postHandle(request, response, handler, modelAndView);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        @Nullable Exception ex) throws Exception {
        if (CollectionUtils.isNotEmpty(this.processors)) {
            for (IApiInterceptorProcessor processor : this.processors) {
                processor.afterCompletion(request, response, handler, ex);
            }
        }
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        if (CollectionUtils.isNotEmpty(this.processors)) {
            for (IApiInterceptorProcessor processor : this.processors) {
                processor.afterConcurrentHandlingStarted(request, response, handler);
            }
        }
    }
}
