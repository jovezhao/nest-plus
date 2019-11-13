package com.zhaofujun.nest.redis;

import com.zhaofujun.nest.core.BeanFinder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RedisAutoConfiguration {

    @Bean
    public RedisCacheProvider cacheProvider(ApplicationContext applicationContext) {
        return new RedisCacheProvider(applicationContext);
    }

}