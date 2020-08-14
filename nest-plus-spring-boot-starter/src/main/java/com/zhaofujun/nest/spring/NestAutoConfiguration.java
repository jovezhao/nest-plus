package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.context.event.DefaultEventBus;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.container.ContainerProvider;
import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.json.JsonCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class NestAutoConfiguration {
//    @Bean
//    public SpringBeanContainerProvider getSpringBeanContainerProvider() {
//        return new SpringBeanContainerProvider();
//    }
//
//    @Bean
//    public NestAspect getNestAspect() {
//        return new NestAspect();
//    }

    @Bean
    public JsonCreator getJsonCreator(BeanFinder beanFinder){
        return new JsonCreator(beanFinder);
    }
    @Bean
    public NestApplication getNestApplication(ContainerProvider containerProvider) {
        return new NestApplication(containerProvider);
    }

    @Bean
    public EventBus getEventBus(BeanFinder beanFinder) {
        return new DefaultEventBus(beanFinder);
    }
}

