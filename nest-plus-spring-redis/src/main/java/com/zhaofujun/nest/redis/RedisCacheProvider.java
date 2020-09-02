package com.zhaofujun.nest.redis;

import com.zhaofujun.nest.cache.CacheProvider;
import com.zhaofujun.nest.json.JsonCreator;
import com.zhaofujun.nest.utils.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisCacheProvider implements CacheProvider {
    public final static String CODE = "REDIS_CACHE_PROVIDER";

    private StringRedisTemplate redisTemplate;
    private JsonCreator jsonCreator;

    public RedisCacheProvider(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.jsonCreator = JsonCreator.getInstance();
    }

    @Override
    public String getCode() {
        return CODE;
    }

    private String getRedisKey(String groupName, String key) {
        return groupName + "_" + key;
    }

    @Override
    public <T> T get(String groupName, String key, Type type) {

        String redisKey = getRedisKey(groupName, key);
        String json = redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isEmpty(json)) return null;
        return jsonCreator.toObj(json, type, true);

    }

    @Override
    public <T> Map<String, T> get(String groupName, Type type, String... keys) {

        Map<String, T> result = new HashMap<>();


        for (String key : keys) {
            T t = get(groupName, key, type);
            if (t != null)
                result.put(key, t);
        }

        return result;
    }

    @Override
    public void put(String groupName, String key, Object value, long idleSeconds) {
        String json = jsonCreator.toJsonString(value);

        String redisKey = getRedisKey(groupName, key);
        redisTemplate.opsForValue().set(redisKey, json);
        redisTemplate.expire(redisKey, idleSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean remove(String groupName, String key) {
        String redisKey = getRedisKey(groupName, key);
        return redisTemplate.delete(redisKey);
    }

    @Override
    public void removeAll(String groupName) {

        Set<String> keys = redisTemplate.keys(groupName + "_*");
        redisTemplate.delete(keys);
    }

    @Override
    public boolean containsKey(String groupName, String key) {
        String redisKey = getRedisKey(groupName, key);
        return redisTemplate.hasKey(redisKey);
    }

    @Override
    public String[] getKeys(String groupName) {
        return redisTemplate.keys(groupName + "_*").toArray(new String[]{});
    }
}
