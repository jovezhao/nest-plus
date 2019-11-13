package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.core.EventBus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class NestApplicationContextAware implements ApplicationContextAware {
    @Autowired
    private EventBus eventBus;

    @Autowired
    private NestApplication nestApplication;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        eventBus.autoRegister();


        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        configurableApplicationContext.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {
            @Override
            public void onApplicationEvent(ContextClosedEvent event) {
                nestApplication.close();
            }
        });
    }
}
