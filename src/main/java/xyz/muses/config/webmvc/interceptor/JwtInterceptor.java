/**
 * Copyright 2022 All rights reserved.
 */

package xyz.muses.config.webmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;

import xyz.muses.constants.redis.cache.RedisCacheConst;
import xyz.muses.utils.JwtUserUtils;
import xyz.muses.web.model.dto.BaseResponseDTO;

/**
 * @author jervis
 * @date 2022/6/22.
 */
public class JwtInterceptor extends AbstractInterceptor {

    /** JWT请求头KEY **/
    public static final String JWT_HEADER_KEY = "Authorization";

    /** redis工具 **/
    private final RedissonClient redisson;
    /** jwt工具 **/
    private final JwtUserUtils jwtUserUtils;

    public JwtInterceptor(RedissonClient redisson, JwtUserUtils jwtUserUtils) {
        super();
        this.redisson = redisson;
        this.jwtUserUtils = jwtUserUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String jwt;
        JwtLocalUser jwtUser, ctxJwtUser;
        if (StringUtils.isBlank(jwt = request.getHeader(JWT_HEADER_KEY)) || !jwt.startsWith("Bearer ")) {
            this.writeError(response, BaseResponseDTO.DEFAULT_RESPONSE_RESULT.PARAM_CHECK_FAIL,
                "Access Authorization missing, please check!");
            return false;
        } else if (null == (jwtUser = jwtUserUtils.verify(jwt.substring(7), JwtLocalUser.class))
            || null == (ctxJwtUser =
                (JwtLocalUser)this.redisson.getBucket(RedisCacheConst.Key.JWT.getCode() + jwtUser.getUserId()).get())) {
            this.writeError(response, BaseResponseDTO.DEFAULT_RESPONSE_RESULT.PARAM_CHECK_FAIL,
                "The Authorization is invalid or expired, please apply again!");
            return false;
        } else {
            logger.info("Access Authorization: {}, jwtUser: {}", jwt, ctxJwtUser);
            JwtUserContext.set(ctxJwtUser);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        JwtUserContext.release();
    }
}
