package com.zhaofujun.nest.redis;

import com.zhaofujun.nest.cache.provider.CacheProvider;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.json.JsonCreator;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisCacheProvider implements CacheProvider {
    public final static String CODE = "REDIS_CACHE_PROVIDER";

    private StringRedisTemplate redisTemplate;
    private JsonCreator jsonCreator;

    public RedisCacheProvider(StringRedisTemplate redisTemplate,BeanFinder beanFinder) {
        this.redisTemplate = redisTemplate;
        this.jsonCreator=new JsonCreator(beanFinder);
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public <T> T get(String groupName, String key, Class<T> clazz) {

        Object json = redisTemplate.opsForHash().get(groupName, key);
        if (json != null)
            return jsonCreator.toObj(json.toString(), clazz);
        return null;

    }

    @Override
    public <T> Map<String, T> get(String groupName, Class<T> clazz, String... keys) {


        Map<String, T> result = new HashMap<>();


        for (String key : keys) {
            T t = get(groupName, key, clazz);
            if (t != null)
                result.put(key, t);
        }

        return result;
    }

    @Override
    public void put(String groupName, String key, Object value, long idleSeconds) {
        String json = jsonCreator.toJsonString(value);

        redisTemplate.opsForHash().put(groupName, key, json);
        redisTemplate.expire(groupName,idleSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean remove(String groupName, String key) {

        return redisTemplate.opsForHash().delete(groupName, key) > 0;

    }

    @Override
    public void removeAll(String groupName) {

        redisTemplate.delete(groupName);
    }

    @Override
    public boolean containsKey(String groupName, String key) {

        return redisTemplate.opsForHash().hasKey(groupName, key);
    }

    @Override
    public String[] getKeys(String groupName) {

        return redisTemplate.opsForHash().keys(groupName).toArray(new String[]{});

    }
}
