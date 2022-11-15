/**
 * Copyright 2022 All rights reserved.
 */

package cn.muses.config.webmvc.interceptor;

/**
 * @author jervis
 * @date 2022/6/22.
 */
public class GlobalUserContext {

    private static final ThreadLocal<GlobalUser> context = ThreadLocal.withInitial(() -> null);

    private GlobalUserContext() {}

    public static GlobalUser get() {
        return context.get();
    }

    public static void set(GlobalUser weComUser) {
        context.set(weComUser);
    }

    public static void release() {
        context.remove();
    }

    public static boolean isLogin() {
        return context.get() != null;
    }
}
