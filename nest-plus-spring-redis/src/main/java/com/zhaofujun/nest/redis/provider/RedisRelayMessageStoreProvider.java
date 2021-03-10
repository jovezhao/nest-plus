package com.zhaofujun.nest.redis.provider;

import com.zhaofujun.nest.context.event.delay.DelayMessageBacklog;
import com.zhaofujun.nest.context.event.delay.DelayMessageStore;
import com.zhaofujun.nest.context.event.message.MessageBacklog;
import com.zhaofujun.nest.json.JsonCreator;
import com.zhaofujun.nest.utils.LockUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisRelayMessageStoreProvider implements DelayMessageStore {

    public final static String CODE = "RedisRelayMessageStoreProvider";

    private String storeKey = "nest_relay";
    private StringRedisTemplate redisTemplate;
    private JsonCreator jsonCreator;


    public RedisRelayMessageStoreProvider(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.jsonCreator = JsonCreator.getInstance();
    }

    @Override
    public void add(DelayMessageBacklog delayMessageBacklog) {
        long score = delayMessageBacklog.getSendTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        MessageBacklog messageBacklog = delayMessageBacklog.getMessageBacklog();
        String json = jsonCreator.toJsonString(messageBacklog);
        redisTemplate.opsForZSet().add(storeKey, json, score);
    }

    @Override
    public List<MessageBacklog> getAndLock() {
        List<MessageBacklog> messageBacklogs = new ArrayList<>();
        LockUtils.runByLock(storeKey, () -> {
            Set<String> messageJsonSet = redisTemplate.opsForZSet().rangeByScore(storeKey, 0, System.currentTimeMillis());

            Object[] values = messageJsonSet.toArray(new String[messageJsonSet.size()]);
            redisTemplate.opsForZSet().remove(storeKey, values);
            messageBacklogs.addAll(messageJsonSet.stream().map(p -> jsonCreator.toObj(p, MessageBacklog.class)).collect(Collectors.toList()));
        });
        return messageBacklogs;
    }

    @Override
    public void clear(String s) {

    }

    @Override
    public String getCode() {
        return CODE;
    }
}
