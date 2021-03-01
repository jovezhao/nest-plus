package com.zhaofujun.nest.redis.identifier;

import com.zhaofujun.nest.utils.identifier.LongIdentifierGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component(RedisLongIdentifierGenerator.CODE)
public class RedisLongIdentifierGenerator implements LongIdentifierGenerator {
    public final static String CODE = "RedisLongIdentifierGenerator";

    @Override
    public String name() {
        return CODE;
    }

    private RedisTemplate redisTemplate;
    private String prefix = "nest_redis_id_";

    public RedisLongIdentifierGenerator(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Long nextValue(String key) {

        return redisTemplate.opsForValue().increment(prefix + key);
    }
}

