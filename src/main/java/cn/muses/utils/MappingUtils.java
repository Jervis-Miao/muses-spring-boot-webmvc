/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import ma.glasnost.orika.MapperFacade;

/**
 * @author jervis
 * @date 2020/12/1.
 */
public class MappingUtils {
    private static MapperFacade mapperFacade;

    private MappingUtils() {}

    /**
     * bean 集合转换
     *
     * @param sourceList 源集合
     * @param destClass 目标集合的类
     * @return 目标集合,not null
     */
    public static <T, K> List<K> beanListConvert(List<T> sourceList, Class<K> destClass) {
        List<K> destList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sourceList)) {
            for (T source : sourceList) {
                destList.add(beanConvert(source, destClass));
            }
        }
        return destList;
    }

    /**
     * bean转换
     *
     * @param source 源
     * @param destClass 目标类
     * @return 目标
     */
    public static <T, K> K beanConvert(T source, Class<K> destClass) {
        return getMapperFacade().map(source, destClass);
    }

    /**
     * bean转换
     *
     * @param source 源
     * @param destObj 目标对象
     */
    public static <T, K> void beanConvert(T source, K destObj) {
        getMapperFacade().map(source, destObj);
    }

    private static MapperFacade getMapperFacade() {
        if (null == mapperFacade) {
            synchronized (MappingUtils.class) {
                if (null == mapperFacade) {
                    mapperFacade = SpringContextUtils.getBean(MapperFacade.class);
                }
            }
        }
        return mapperFacade;
    }
}
