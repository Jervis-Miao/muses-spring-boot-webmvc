/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.constants;

/**
 * @author jervis
 * @date 2020/12/2.
 */
public interface MvcConstant {

    /** 第三方api接口前缀 **/
    String EXTERNAL_API_URL_PREFIX = "/external";

    /** 登录接口前缀 **/
    String LOGIN_URL_PREFIX = "/user/login";
    String LOGIN_IN_URL = LOGIN_URL_PREFIX.concat("/in");
}
