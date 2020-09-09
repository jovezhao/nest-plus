package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.cache.CacheConfiguration;
import com.zhaofujun.nest.context.event.EventConfiguration;
import com.zhaofujun.nest.spring.configuration.MessageProperties;
import com.zhaofujun.nest.spring.configuration.NestProperties;
import com.zhaofujun.nest.standard.EventBus;
import com.zhaofujun.nest.standard.EventHandler;
import com.zhaofujun.nest.utils.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NestApplicationContextAware implements ApplicationContextAware {

    @Value("${nest.events.auto:true}")
    private boolean autoRegister;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        NestApplication nestApplication = NestApplication.current();
        MessageProperties messageProperties = applicationContext.getBean(MessageProperties.class);
        //使用properties配置NestApplication
        if (messageProperties != null) {
            if (!StringUtils.isEmpty(messageProperties.getConverter()))
                nestApplication.getMessageConfiguration().setConverter(messageProperties.getConverter());
            if (!StringUtils.isEmpty(messageProperties.getResendStore()))
                nestApplication.getMessageConfiguration().setResendStore(messageProperties.getResendStore());
            if (!StringUtils.isEmpty(messageProperties.getStore()))
                nestApplication.getMessageConfiguration().setStore(messageProperties.getStore());
        }
        NestProperties nestProperties = applicationContext.getBean(NestProperties.class);
        if (nestApplication != null) {
            nestProperties.getCaches().forEach((p, q) -> {
                CacheConfiguration cacheConfiguration = new CacheConfiguration();
                cacheConfiguration.setCode(p);
                cacheConfiguration.setName(StringUtils.isEmpty(q.getName()) ? p : q.getName());
                cacheConfiguration.setIdleSeconds(q.getIdleSeconds());
                cacheConfiguration.setProviderCode(q.getProvider());

                nestApplication.getConfigurationManager().addConfigurationItem(cacheConfiguration);
            });
            nestProperties.getEvents().forEach((p, q) -> {
                EventConfiguration eventConfiguration = new EventConfiguration();
                eventConfiguration.setCode(p);
                eventConfiguration.setMessageChannelCode(q);

                nestApplication.getConfigurationManager().addConfigurationItem(eventConfiguration);
            });
        }


        nestApplication.setContainerProvider(new SpringBeanContainerProvider(applicationContext));
        EventBus eventBus = applicationContext.getBean(EventBus.class);


        if (autoRegister) {
            Map<String, EventHandler> beansOfType = applicationContext.getBeansOfType(EventHandler.class);
            beansOfType.forEach((p, q) -> eventBus.registerHandler(q));
        }
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        configurableApplicationContext.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {
            @Override
            public void onApplicationEvent(ContextClosedEvent event) {
                nestApplication.close();
            }
        });

        nestApplication.start();

    }
}
