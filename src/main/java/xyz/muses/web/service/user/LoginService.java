/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.web.service.user;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import xyz.muses.config.webmvc.interceptor.JwtLocalUser;
import xyz.muses.config.webmvc.interceptor.JwtUserContext;
import xyz.muses.constants.CacheConstant;
import xyz.muses.constants.ResultErrorConstant;
import xyz.muses.exceptions.MusesException;
import xyz.muses.utils.JwtUserUtils;
import xyz.muses.web.service.BaseService;

/**
 * @author jervis
 * @date 2024/8/22
 */
@Service
public class LoginService extends BaseService {

    @Value("${jwt.expires.ttl}")
    private long jwtTtl;

    @Autowired
    private JwtUserUtils jwtUserUtils;

    @Autowired
    private RedissonClient redisson;

    /**
     * 用户登录
     *
     * @param mobile
     * @param code
     * @return
     * @throws MusesException
     */
    public String loginIn(String mobile, String code) throws MusesException {
        // TODO 校验手机验证码是否正确
        if (StringUtils.isNotBlank(code)) {
            // TODO 检查客户，没有需要生成
            JwtLocalUser user;
            if (null != (user = this.getUser(mobile))) {
                String jwt = jwtUserUtils.generate(user);
                // TODO 设置服务端用户缓存, 过期时间比jwt多5秒
                this.setUserCache(user, jwtTtl + 5 * 1000);
                return jwt;
            }
        }
        throw new MusesException(ResultErrorConstant.Error.CUSTOM, "用户不存在");
    }

    public void loginOut() {
        JwtLocalUser user = JwtUserContext.get();
        // 立即失效服务端用户缓存
        this.setUserCache(user, 0);
    }

    /**
     * 续期jwt
     * 
     * @return
     * @throws MusesException
     */
    public String refreshJwt() throws MusesException {
        JwtLocalUser jwtUser = JwtUserContext.get();
        String jwt;
        if (StringUtils.isNotBlank(jwt = jwtUserUtils.generate(jwtUser))) {
            // TODO 设置服务端用户缓存, 过期时间比jwt多5秒
            this.setUserCache(jwtUser, jwtTtl + 5 * 1000);
            return jwt;
        }
        throw new MusesException(ResultErrorConstant.Error.GENERAL, "续期失败");
    }

    private JwtLocalUser getUser(String mobile) {
        JwtLocalUser user = new JwtLocalUser();
        user.setUserId(1L);
        user.setUserName("test");
        return user;
    }

    private void setUserCache(JwtLocalUser user, long ttl) {
        RBucket<Object> bucket = redisson.getBucket(CacheConstant.Key.JWT.getValue() + user.getUserId());
        bucket.set(user);
        bucket.expire(ttl, TimeUnit.MILLISECONDS);
    }
}
