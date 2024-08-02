/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.config.webmvc;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author jervis
 * @date 2021/6/12.
 */
public class RequestParameterWrapper extends HttpServletRequestWrapper {

    /**
     * 所有参数的Map集合
     */
    private Map<String, String[]> parameterMap;

    public RequestParameterWrapper(HttpServletRequest request) {
        super(request);
        parameterMap = new HashMap<>(request.getParameterMap());
    }

    /**
     * 获取所有参数名
     *
     * @return 返回所有参数名
     */
    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<>(this.parameterMap.keySet());
        return vector.elements();
    }

    /**
     * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
     *
     * @param name 指定参数名
     * @return 指定参数名的值
     */
    @Override
    public String getParameter(String name) {
        if (this.parameterMap.containsKey(name)) {
            String[] results = this.getParameterValues(name);
            return results[0];
        }
        return null;
    }

    /**
     * 获取指定参数名的所有值的数组，如：checkbox的所有数据 接收数组变量 ，如checkobx类型
     *
     * @param name
     */
    @Override
    public String[] getParameterValues(String name) {
        return this.parameterMap.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.parameterMap;
    }

    public void putParameter(String key, String[] values) {
        this.parameterMap.put(key, values);
    }

    public void setParameterMap(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }
}
