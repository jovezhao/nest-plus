package com.zhaofujun.nest.automapper;

import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.nest.automapper.converters.LongIdentifierConverter;
import com.zhaofujun.nest.automapper.converters.StringIdentifierConverter;
import com.zhaofujun.nest.automapper.converters.UUIdentifierConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoMapperAutoConfiguration {

    @Bean
    public AutoMapper getAutoMapper() {
        return new AutoMapper();
    }

    @Bean
    public AutoMapperAware autoMapperAware() {
        return new AutoMapperAware();
    }

    @Bean
    public StringIdentifierConverter stringIdentifierConverter() {
        return new StringIdentifierConverter();
    }

    @Bean
    public UUIdentifierConverter uuIdentifierConverter() {
        return new UUIdentifierConverter();
    }

    @Bean
    public LongIdentifierConverter longIdentifierConverter() {
        return new LongIdentifierConverter();
    }
}
