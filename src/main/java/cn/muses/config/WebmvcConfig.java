/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.muses.config.webmvc.filter.RequestWrapperFilter;
import cn.muses.config.webmvc.interceptor.ApiInterceptor;
import cn.muses.config.webmvc.interceptor.GlobalTokenInterceptor;
import cn.muses.config.webmvc.interceptor.IApiInterceptorProcessor;
import cn.muses.constants.MvcConstant;
import cn.muses.web.model.dto.BaseResponseDTO;
import cn.muses.exceptions.MusesException;
import cn.muses.utils.SpringContextUtils;

/**
 * @author jervis
 * @date 2020/12/2.
 */
@Configuration
public class WebmvcConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public FilterRegistrationBean requestWrapperFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestWrapperFilter());
        registration.addUrlPatterns(MvcConstant.API_URL_PREFIX + "/*");
        registration.setName("requestWrapperFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * Mvc配置
     *
     * @param redissonUtils
     * @param objectMapper
     * @param abuseInterceptor
     * @param processors
     * @return
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer(RedissonClient redisson, ObjectMapper objectMapper,
        List<IApiInterceptorProcessor> processors) {
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new ApiInterceptor(processors))
                    .addPathPatterns(MvcConstant.API_URL_PREFIX + "/**");
                // 全局用户上下文拦截器
                registry.addInterceptor(new GlobalTokenInterceptor(redisson))
                    .addPathPatterns(MvcConstant.MATERIAL_SALES_PATH_PREFIX + "/**")
                    .addPathPatterns(MvcConstant.SALE_ESTIMATE_PATH_PREFIX + "/**");
            }

            @Override
            public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
                resolvers.add(new AbstractHandlerExceptionResolver() {
                    @Override
                    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                        Object handler, Exception ex) {
                        logger.error(String.format("exception when request url: [%s]", request.getRequestURI()), ex);
                        String message = ex.getMessage();
                        if (ex instanceof MissingServletRequestParameterException) {
                            BaseResponseDTO.DEFAULT_RESPONSE_RESULT errEnum =
                                BaseResponseDTO.DEFAULT_RESPONSE_RESULT.MISS_REQUEST_PARAM;
                            BaseResponseDTO<Void> dto = new BaseResponseDTO<>();
                            dto.setRet(errEnum.value());
                            dto.addError(String.format("%s: %s", errEnum.desc(), message));
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_OK);
                            try {
                                Writer writer = response.getWriter();
                                writer.write(objectMapper.writeValueAsString(dto));
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return new ModelAndView();
                        } else if (ex instanceof MusesException) {
                            String msg;
                            if (StringUtils.isNotBlank(msg = ((MusesException)ex).getError().desc())
                                || StringUtils.isNotBlank(msg = ex.getMessage())) {
                                return handleEx(response, BaseResponseDTO.DEFAULT_RESPONSE_RESULT.BIZ_ERROR, ex);
                            }
                        }
                        return handleEx(response, BaseResponseDTO.DEFAULT_RESPONSE_RESULT.SYSTEM_ERROR, ex);
                    }
                });
            }

            /**
             * 解决resources下面静态资源无法访问
             *
             * @param registry
             */
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // 首页
                registry.addResourceHandler("/index.html").addResourceLocations("classpath:/static/");
                // favicon.ico
                registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");
                // 403
                registry.addResourceHandler("/403.html").addResourceLocations("classpath:/static/");
            }
        };

        return webMvcConfigurer;
    }

    private ModelAndView handleEx(HttpServletResponse response, BaseResponseDTO.DEFAULT_RESPONSE_RESULT errEnum,
        Exception ex) {
        BaseResponseDTO<Void> dto = new BaseResponseDTO<Void>();
        logger.error("统一异常处理捕获未处理异常:", ex);
        dto.setRet(errEnum.value());
        dto.addError(StringUtils.isNotBlank(errEnum.desc()) ? errEnum.desc() : ex.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            Writer writer = response.getWriter();
            writer.write(SpringContextUtils.getBean(ObjectMapper.class).writeValueAsString(dto));
            writer.flush();
        } catch (IOException e) {
            logger.error("异常输出处理失败", e);
        }
        return new ModelAndView();
    }
}
