package com.zhaofujun.nest.activemq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;


@Configuration
public class ActivemqAutoConfiguration {

    @Bean
    public ActiveMQMessageChannel activeMQMessageChannel( JmsTemplate jmsTemplate) {
        return new ActiveMQMessageChannel( jmsTemplate);
    }

}
