package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.ContainerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class SpringBeanContainerProvider implements ContainerProvider {

    private Logger logger = LoggerFactory.getLogger(SpringBeanContainerProvider.class);

    @Autowired
    private ConfigurableApplicationContext applicationContext;


    @Override
    public void initialize() {
//        applicationContext.refresh();
//        applicationContext.start();
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (BeansException ex) {
            logger.warn("从容器中没有找到类型为" + clazz.getName() + "的bean");
            return null;
        }
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String name) {
        try {
            return applicationContext.getBean(name, clazz);
        } catch (BeansException ex) {
            logger.warn("从容器中没有找到name为" + name + ",类型为" + clazz.getName() + "的bean");
            return null;
        }
    }

    @Override
    public <T> Set<T> getInstances(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz).entrySet()
                .stream()
                .map(p -> p.getValue())
                .collect(Collectors.toSet());
    }


}
