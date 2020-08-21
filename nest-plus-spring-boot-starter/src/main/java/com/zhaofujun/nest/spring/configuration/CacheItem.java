package com.zhaofujun.nest.spring.configuration;

import com.zhaofujun.nest.cache.DefaultCacheProvider;

public class CacheItem {
    /**
     * 策略提供者
     */
    private String provider = DefaultCacheProvider.PROVIDER_CODE;
    /**
     * 过期时间
     */
    private long idleSeconds = 60 * 60 * 24;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public long getIdleSeconds() {
        return idleSeconds;
    }

    public void setIdleSeconds(long idleSeconds) {
        this.idleSeconds = idleSeconds;
    }
}
