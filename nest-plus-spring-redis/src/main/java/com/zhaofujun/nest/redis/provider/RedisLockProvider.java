package com.zhaofujun.nest.redis.provider;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.configuration.LockConfiguration;
import com.zhaofujun.nest.lock.LockProvider;
import com.zhaofujun.nest.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisLockProvider implements LockProvider {

    public final static String CODE = "RedisLockProvider";

    private RedisTemplate redisTemplate;

    public RedisLockProvider(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String prefix = "nest_redis_lock_";

    @Override
    public boolean lock(String key) {
        String requestId = UUID.randomUUID().toString();
        Long start = System.currentTimeMillis();
        LockConfiguration lockConfiguration = NestApplication.current().getLockConfiguration();
        //自旋，在一定时间内获取锁，超时则返回错误
        for (; ; ) {
            //Set命令返回OK，则证明获取锁成功
            Boolean ret = redisTemplate.opsForValue().setIfAbsent(prefix + key, requestId, lockConfiguration.getTimeout(), TimeUnit.MILLISECONDS);
            if (ret) {
                return true;
            }
            //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
            long end = System.currentTimeMillis() - start;
            if (end >= lockConfiguration.getTimeout()) {
                return false;
            }
        }
    }

    @Override
    public void unlock(String key) {
        if (!StringUtils.isEmpty(key)) {
            redisTemplate.delete(prefix + key);
        }
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
