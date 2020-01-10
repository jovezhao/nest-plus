package com.zhaofujun.nest.rocketmq;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.ContainerProvider;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RocketMQAutoConfiguration {

    @Bean
    public RocketMQMessageChannel rocketMQMessageChannel(ContainerProvider containerProvider, RocketMQProperties rocketMQProperties, RocketMQTemplate rocketMQTemplate, NestApplication nestApplication) {
        return new RocketMQMessageChannel(containerProvider, rocketMQProperties, rocketMQTemplate, nestApplication);
    }

}
