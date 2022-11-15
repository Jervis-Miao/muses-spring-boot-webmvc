/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config;

import cn.muses.utils.SpringContextUtils;

/**
 * <pre>
 *     将dubbo服务bean交给spring托管
 *     此方式并不会生产额外的bean对象，仅仅是对象托管
 *     这里主要考虑一些static方法需要通过{@link SpringContextUtils#getBean}的方式来调用服务
 *     另一方面，托管后也会拥有Spring的AOP特性
 *
 *     特别的：由于依赖服务较多，请将服务配置到相对应的应用配置子类当中
 * </pre>
 *
 * @author jervis
 * @date 2020/12/16.
 */
public abstract class AbstractDubboProviderConfig {}
