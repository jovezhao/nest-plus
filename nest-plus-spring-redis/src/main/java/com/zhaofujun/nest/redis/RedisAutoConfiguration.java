package com.zhaofujun.nest.redis;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableCaching
public class RedisAutoConfiguration {

    @Bean
    public RedisCacheProvider cacheProvider(StringRedisTemplate stringRedisTemplate) {
        return new RedisCacheProvider(stringRedisTemplate);
    }

    @Bean
    public RedisSequenceFactory redisSequenceFactory(StringRedisTemplate stringRedisTemplate) {
        return new RedisSequenceFactory(stringRedisTemplate);
    }
}