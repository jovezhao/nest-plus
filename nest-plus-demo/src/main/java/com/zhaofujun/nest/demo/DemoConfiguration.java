package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.configuration.CacheConfiguration;
import com.zhaofujun.nest.configuration.EventConfiguration;
import com.zhaofujun.nest.context.event.store.DefaultMessageStore;
import com.zhaofujun.nest.redis.RedisCacheProvider;
//import com.zhaofujun.nest.rocketmq.RocketMQMessageChannel;
import com.zhaofujun.nest.utils.EntityCacheUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoConfiguration   {

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
}
