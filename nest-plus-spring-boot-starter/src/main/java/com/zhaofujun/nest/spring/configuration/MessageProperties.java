package com.zhaofujun.nest.spring.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("nest.message")
public class MessageProperties {
    private String store;
    private String converter;
    private String resendStore;
    private String delayStore;

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getConverter() {
        return converter;
    }

    public void setConverter(String converter) {
        this.converter = converter;
    }

    public String getResendStore() {
        return resendStore;
    }

    public void setResendStore(String resendStore) {
        this.resendStore = resendStore;
    }

    public String getDelayStore() {
        return delayStore;
    }

    public void setDelayStore(String delayStore) {
        this.delayStore = delayStore;
    }
}
