package com.zhaofujun.nest.activemq;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.ContainerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;


@Configuration
public class ActivemqAutoConfiguration {

    @Bean
    public ActiveMQMessageChannel activeMQMessageChannel(ContainerProvider containerProvider, JmsTemplate jmsTemplate, NestApplication nestApplication) {
        return new ActiveMQMessageChannel(containerProvider, jmsTemplate, nestApplication);
    }

}
