/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import xyz.muses.framework.common.utils.JsonMapper;

/**
 * @author jervis
 * @date 2024/8/22
 */
public class JwtUserUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String REFRESH_KEY = "refreshToken";
    private static final String USER_KEY = "userInfo";

    private final Algorithm algorithm;
    private final long ttl;

    private final JsonMapper jsonMapper;

    public JwtUserUtils(String secret, long ttl, JsonMapper jsonMapper) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.ttl = ttl;
        this.jsonMapper = jsonMapper;
    }

    /**
     * 生成JWT
     *
     * @param jwtUser 用户信息
     * @return JWT
     * @param <T> 用户
     */
    public <T extends JwtUser> String generate(T jwtUser) {
        try {
            Date iat = new Date();
            Date refreshExp = new Date(iat.getTime() + this.ttl + 30L * 24 * 60 * 60 * 1000);
            Date exp = new Date(iat.getTime() + this.ttl);
            Map<String, Object> userInfo = this.jsonMapper.fromJson(this.jsonMapper.toJson(jwtUser),
                this.jsonMapper.contructMapType(HashMap.class, String.class, Object.class));
            return JWT.create()
                .withIssuer(jwtUser.getUserName())
                .withSubject(jwtUser.getUserId().toString())
                .withIssuedAt(iat)
                .withExpiresAt(exp)
                .withClaim(REFRESH_KEY, JWT.create().withIssuer(jwtUser.getUserName())
                    .withSubject(jwtUser.getUserId().toString())
                    .withIssuedAt(iat)
                    .withExpiresAt(refreshExp)
                    .sign(this.algorithm))
                .withClaim(USER_KEY, userInfo)
                .sign(this.algorithm);
        } catch (Exception e) {
            logger.error("generate JWT exception: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 解析并认证Token
     * 
     * @param token JWT
     * @param clazz 用户对象类型
     * @return 用户信息
     * @param <T> 用户泛型
     */
    public <T extends JwtUser> T verify(String token, Class<T> clazz) {
        try {
            DecodedJWT verify;
            Map<String, Object> userMap;
            if (null == (verify = JWT.require(this.algorithm).build().verify(token))
                || null == (verify.getClaim(USER_KEY))
                || MapUtils.isEmpty(userMap = verify.getClaim(USER_KEY).asMap())) {
                return null;
            }
            return this.jsonMapper.fromJson(this.jsonMapper.toJson(userMap), clazz);
        } catch (Exception e) {
            logger.error("verify JWT exception: {}", e.getMessage(), e);
            return null;
        }
    }
}
