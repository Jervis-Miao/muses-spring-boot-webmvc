/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.config.webmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截处理器
 *
 * @author jervis
 * @date 2020/12/2.
 */
public interface IApiInterceptorProcessor extends Ordered {

    /**
     * 前置处理
     *
     * @param request
     * @param requestBody
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    default boolean preHandle(HttpServletRequest request, String requestBody, HttpServletResponse response,
        Object handler) throws Exception {
        return true;
    }

    /**
     * 后置处理
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        @Nullable ModelAndView modelAndView) throws Exception {
        return;
    }

    /**
     * 整个请求处理完毕，在视图渲染完毕时回调，一般用于资源的清理或性能的统计<br/>
     * 在多个拦截器调用的过程中, afterCompletion是否取决于preHandler是否=true
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        @Nullable Exception ex) throws Exception {
        return;
    }

    /**
     * 若Controller方法为异步调用，则执行拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */
    default void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        return;
    }
}
