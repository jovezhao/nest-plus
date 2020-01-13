package com.zhaofujun.nest.rabbitmq;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.ContainerProvider;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQAutoConfiguration {

    @Bean
    public RabbitMQMessageChannel rabbitMQMessageChannel(ContainerProvider containerProvider, DefaultMessageListenerContainer defaultMessageListenerContainer, AmqpTemplate amqpTemplate, AmqpAdmin amqpAdmin, NestApplication nestApplication) {
        return new RabbitMQMessageChannel(defaultMessageListenerContainer, containerProvider, amqpTemplate, amqpAdmin, nestApplication);
    }


    @Bean
    public DefaultMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        return new DefaultMessageListenerContainer(connectionFactory);

    }
}
