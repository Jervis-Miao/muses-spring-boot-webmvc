/**
 * Copyright 2022 All rights reserved.
 */

package xyz.muses.config.webmvc.interceptor;

/**
 * @author jervis
 * @date 2022/6/22.
 */
public class JwtUserContext {

    private static final ThreadLocal<JwtLocalUser> context = ThreadLocal.withInitial(() -> null);

    private JwtUserContext() {}

    public static JwtLocalUser get() {
        return context.get();
    }

    public static void set(JwtLocalUser jwtLocalUser) {
        context.set(jwtLocalUser);
    }

    public static void release() {
        context.remove();
    }

    public static boolean isLogin() {
        return context.get() != null;
    }
}
