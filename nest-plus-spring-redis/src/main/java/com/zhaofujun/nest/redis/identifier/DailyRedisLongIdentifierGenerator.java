package com.zhaofujun.nest.redis.identifier;

import com.zhaofujun.nest.utils.identifier.LongIdentifierGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

@Component(DailyRedisLongIdentifierGenerator.CODE)
public class DailyRedisLongIdentifierGenerator implements LongIdentifierGenerator {

    public final static String CODE = "DailyRedisLongIdentifierGenerator";

    private RedisTemplate redisTemplate;
    private String prefix="nest_redis_id_daily_";

    public DailyRedisLongIdentifierGenerator(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String name() {
        return CODE;
    }

    @Override
    public Long nextValue(String key) {
        Long expireTime = LocalDate.now().atTime(23, 59, 59, 999).toInstant(ZoneOffset.of("+8")).toEpochMilli() - System.currentTimeMillis();
        redisTemplate.expire(prefix+key, expireTime, TimeUnit.MILLISECONDS);
        return redisTemplate.opsForValue().increment(prefix+key);
    }
}
