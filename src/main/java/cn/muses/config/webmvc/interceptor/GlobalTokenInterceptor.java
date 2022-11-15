/**
 * Copyright 2022 All rights reserved.
 */

package cn.muses.config.webmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import cn.muses.constants.CacheConstant;
import cn.muses.web.model.dto.BaseResponseDTO;
import cn.muses.utils.TokenUtils;

/**
 * @author jervis
 * @date 2022/6/22.
 */
public class GlobalTokenInterceptor extends AbstractInterceptor {

    /**
     * 全局TOKEN请求头KEY
     */
    public static final String GLOBAL_TOKEN_HEADER_KEY = "gToken";

    /**
     * redis工具
     */
    private RedissonClient redisson;

    public GlobalTokenInterceptor(RedissonClient redisson) {
        super();
        this.redisson = redisson;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String gToken;
        RBucket<GlobalUser> globalUser;
        if (StringUtils.isBlank(gToken = request.getHeader(GLOBAL_TOKEN_HEADER_KEY))) {
            this.writeError(response, BaseResponseDTO.DEFAULT_RESPONSE_RESULT.PARAM_CHECK_FAIL,
                "Access gToken missing, please check!");
            return false;
        } else if (!TokenUtils.checkToken(gToken)
            || null == (globalUser = this.redisson.getBucket(CacheConstant.Key.GLOBAL_TOKEN.getValue() + gToken))
                .get()) {
            this.writeError(response, BaseResponseDTO.DEFAULT_RESPONSE_RESULT.PARAM_CHECK_FAIL,
                "The gToken is invalid or expired, please apply again!");
            return false;
        } else {
            logger.info("Access gToken: {}, globalUser: {}", gToken, globalUser);
            GlobalUserContext.set(globalUser.get());
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        GlobalUserContext.release();
    }
}
