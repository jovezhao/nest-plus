package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.context.event.DefaultEventBus;
import com.zhaofujun.nest.standard.EventBus;
import com.zhaofujun.nest.utils.identifier.impl.LocalLongGenerator;
import com.zhaofujun.nest.utils.identifier.impl.SnowflakeLongGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(ApplicationServiceScannerRegistrar.class)
public class NestAutoConfiguration {

//    @Bean
//    public JsonCreator getJsonCreator() {
//        return new JsonCreator();
//    }

    @Bean
    public EventBus getEventBus() {
        return new DefaultEventBus();
    }

    @Bean("snowflakeLongGenerator")
    public SnowflakeLongGenerator snowflakeLongIdentifierGenerator(){
        return new SnowflakeLongGenerator();
    }
    @Bean("localLongGenerator")
public LocalLongGenerator localLongIdentifierGenerator(){
        return new LocalLongGenerator();
    }
}

