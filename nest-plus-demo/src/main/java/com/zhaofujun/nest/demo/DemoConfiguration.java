package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.configuration.CacheConfiguration;
import com.zhaofujun.nest.configuration.EventConfiguration;
import com.zhaofujun.nest.context.event.store.DefaultMessageStore;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import com.zhaofujun.nest.redis.RedisCacheProvider;
//import com.zhaofujun.nest.rocketmq.RocketMQMessageChannel;
import com.zhaofujun.nest.utils.EntityCacheUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class DemoConfiguration implements ApplicationContextAware {

//    @Bean
//    public EventConfiguration testEventConfiguration() {
//        EventConfiguration eventConfiguration = new EventConfiguration();
//        eventConfiguration.setEventCode(DemoEventData.Code);
//        eventConfiguration.setMessageChannelCode(RocketMQMessageChannel.CHANNEL_CODE);
//        return eventConfiguration;
//    }

    @Bean
    public CacheConfiguration messageStoreConfiguration() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setCacheCode(DefaultMessageStore.CACHE_CODE);
        cacheConfiguration.setName("事件消息存储策略");
        cacheConfiguration.setProviderCode(RedisCacheProvider.CODE);
        return cacheConfiguration;
    }

    @Bean
    public CacheConfiguration entityCacheConfiguration() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setCacheCode(EntityCacheUtils.getCacheCode());
        cacheConfiguration.setName("实体缓存策略");
        cacheConfiguration.setProviderCode(RedisCacheProvider.CODE);
        return cacheConfiguration;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        NestApplication bean = applicationContext.getBean(NestApplication.class);
        bean.addApplicationListener(new ApplicationListener() {
            @Override
            public void applicationStarted(ApplicationEvent applicationEvent) {
                System.out.println("应用启动");
            }

            @Override
            public void applicationClosed(ApplicationEvent applicationEvent) {
                System.out.println("应用结束");
            }
        });
        bean.addServiceContextListener(new ServiceContextListener() {
            @Override
            public void serviceCreated(ServiceEvent serviceEvent) {
                System.out.println("serviceCreated");

            }

            @Override
            public void serviceMethodStart(ServiceEvent serviceEvent, Method method) {
                System.out.println("serviceMethodStart");

            }

            @Override
            public void serviceMethodEnd(ServiceEvent serviceEvent, Method method) {
                System.out.println("serviceMethodEnd");

            }

            @Override
            public void beforeCommit(ServiceEvent serviceEvent) {
                System.out.println("beforeCommit");

            }

            @Override
            public void committed(ServiceEvent serviceEvent) {
                System.out.println("committed");

            }

            @Override
            public void serviceEnd(ServiceEvent serviceEvent) {
                System.out.println("serviceEnd");

            }
        });
    }
}
