package com.zhaofujun.nest.spring.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@ConfigurationProperties("nest")
public class NestProperties {

    private Map<String, CacheItem> caches = new LinkedHashMap<>();

    private Map<String, String> events = new LinkedHashMap<>();


    public Map<String, CacheItem> getCaches() {
        return caches;
    }

    public void setCaches(Map<String, CacheItem> caches) {
        this.caches = caches;
    }

    public Map<String, String> getEvents() {
        return events;
    }

    public void setEvents(Map<String, String> events) {
        this.events = events;
    }
}


