/**
 * Copyright 2022 All rights reserved.
 */

package xyz.muses.config.webmvc.interceptor;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import xyz.muses.web.model.dto.BaseResponseDTO;
import xyz.muses.utils.JsonMapper;
import xyz.muses.utils.SpringContextUtils;

/**
 * @author jervis
 * @date 2022/6/22.
 */
public abstract class AbstractInterceptor extends HandlerInterceptorAdapter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 免鉴权地址
     *
     * @param url
     * @return
     */
    protected boolean isAllowedPath(String url) {
        return false;
    }

    /**
     * 非必须鉴权地址
     *
     * @param url
     * @return
     */
    protected boolean isOptional(String url) {
        return false;
    }

    /**
     * 响应报错信息
     *
     * @param response
     * @param ret
     * @param errorMsg
     */
    protected final void writeError(HttpServletResponse response, BaseResponseDTO.DEFAULT_RESPONSE_RESULT ret,
        String errorMsg) {
        try {
            BaseResponseDTO res = BaseResponseDTO.builder().ret(ret.value()).addError(errorMsg).build();
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            Writer writer = response.getWriter();
            writer.write(SpringContextUtils.getBean(JsonMapper.class).toJson(res));
            writer.flush();
        } catch (IOException e) {
            logger.error("Write errorMsg exception: {}", e.getMessage(), e);
        }
    }
}
