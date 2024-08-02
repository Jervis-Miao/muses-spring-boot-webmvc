/**
 * Copyright 2022 All rights reserved.
 */

package xyz.muses.config.orika;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;

/**
 * @author jervis
 * @date 2022/11/14.
 */
public abstract class AbstractOrikaMapperFactoryConfigurer
    implements OrikaMapperFactoryConfigurer, ApplicationContextAware {

    private MapperFactory mapperFactory;

    private ApplicationContext ac;

    @Override
    public void configure(MapperFactory orikaMapperFactory) {
        this.mapperFactory = orikaMapperFactory;

        // (1)先添加converter,converter可被mapper使用
        this.addConverter(this.mapperFactory.getConverterFactory());

        // (2)添加 Custom Mapper,Custom Mapper继承ma.glasnost.orika.CustomMapper
        Map<String, Mapper> mappers = ac.getBeansOfType(Mapper.class);
        for (final Mapper mapper : mappers.values()) {
            addMapper(mapper);
        }

        // (3)添加Fluid Mapper
        addFluidMapper(mapperFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }

    private void addMapper(Mapper<?, ?> mapper) {
        mapperFactory.classMap(mapper.getAType(), mapper.getBType()).byDefault().customize((Mapper)mapper).register();
    }

    /**
     * <pre>
     *     identified converter for field convert
     *     non-identified converter for global convert
     * </pre>
     *
     * @param converterFactory
     */
    abstract protected void addConverter(ConverterFactory converterFactory);

    abstract protected void addFluidMapper(MapperFactory mapperFactory);
}
