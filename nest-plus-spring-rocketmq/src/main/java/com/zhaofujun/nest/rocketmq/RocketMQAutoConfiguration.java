package com.zhaofujun.nest.rocketmq;

import com.zhaofujun.nest.NestApplication;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RocketMQAutoConfiguration {

    @Bean
    public RocketMQMessageChannel rocketMQMessageChannel(RocketMQProperties rocketMQProperties, RocketMQTemplate rocketMQTemplate) {
        return new RocketMQMessageChannel(rocketMQProperties, rocketMQTemplate, NestApplication.current());
    }

}
