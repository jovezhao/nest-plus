package com.zhaofujun.nest.redis;

import com.zhaofujun.nest.redis.identifier.DailyRedisLongIdentifierGenerator;
import com.zhaofujun.nest.redis.identifier.RedisLongIdentifierGenerator;
import com.zhaofujun.nest.redis.identifier.RedisSequenceFactory;
import com.zhaofujun.nest.redis.provider.RedisCacheProvider;
import com.zhaofujun.nest.redis.provider.RedisLockProvider;
import com.zhaofujun.nest.redis.provider.RedisRelayMessageStoreProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableCaching
public class RedisAutoConfiguration {

    @Bean
    public RedisCacheProvider cacheProvider(StringRedisTemplate stringRedisTemplate) {
        return new RedisCacheProvider(stringRedisTemplate);
    }

    @Bean
    public RedisRelayMessageStoreProvider storeProvider(StringRedisTemplate stringRedisTemplate){
        return new RedisRelayMessageStoreProvider(stringRedisTemplate);
    }
    @Bean
    public RedisSequenceFactory redisSequenceFactory(StringRedisTemplate stringRedisTemplate) {
        return new RedisSequenceFactory(stringRedisTemplate);
    }
    @Bean
    public RedisLockProvider lockProvider(RedisTemplate stringRedisTemplate) {
        return new RedisLockProvider(stringRedisTemplate);
    }
    @Bean(RedisLongIdentifierGenerator.CODE)
    public RedisLongIdentifierGenerator redisLongIdentifierGenerator(RedisTemplate redisTemplate){
        return new RedisLongIdentifierGenerator(redisTemplate);
    }

    @Bean(DailyRedisLongIdentifierGenerator.CODE)
    public DailyRedisLongIdentifierGenerator dailyRedisLongIdentifierGenerator(RedisTemplate redisTemplate){
        return new DailyRedisLongIdentifierGenerator(redisTemplate);
    }
}