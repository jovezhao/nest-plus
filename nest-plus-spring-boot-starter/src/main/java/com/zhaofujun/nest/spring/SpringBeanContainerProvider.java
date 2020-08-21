package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.ContainerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class SpringBeanContainerProvider implements ContainerProvider {
    public SpringBeanContainerProvider(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private ApplicationContext applicationContext;

    @Override
    public <T> Set<T> getInstances(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz).entrySet()
                .stream()
                .map(p -> p.getValue())
                .collect(Collectors.toSet());
    }


}
