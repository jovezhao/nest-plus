package com.zhaofujun.nest.automapper;

import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.automapper.converter.Converter;
import com.zhaofujun.automapper.mapping.ClassMapping;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AutoMapperAware implements ApplicationContextAware {
    @Autowired
    private AutoMapper autoMapper;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        applicationContext.getBeansOfType(ClassMapping.class).forEach((p, q) -> {
            autoMapper.getClassMappingManager().registerClassMapping(q);
        });

        applicationContext.getBeansOfType(Converter.class).forEach((p, q) -> {
            autoMapper.getConverterManager().addConverter(q);
        });
    }
}
