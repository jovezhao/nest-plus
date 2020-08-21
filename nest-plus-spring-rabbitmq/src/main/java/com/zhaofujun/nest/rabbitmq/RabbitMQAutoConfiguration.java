package com.zhaofujun.nest.rabbitmq;

import com.zhaofujun.nest.NestApplication;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQAutoConfiguration {

    @Bean
    public RabbitMQMessageChannel rabbitMQMessageChannel(DefaultMessageListenerContainer defaultMessageListenerContainer, AmqpTemplate amqpTemplate, AmqpAdmin amqpAdmin) {
        return new RabbitMQMessageChannel(defaultMessageListenerContainer, amqpTemplate, amqpAdmin, NestApplication.current());
    }


    @Bean
    public DefaultMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        return new DefaultMessageListenerContainer(connectionFactory);

    }
}
