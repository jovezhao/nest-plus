package com.zhaofujun.nest.rabbitmq;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;

/**
 *
 **/
public class RabbitMQMessageChannel extends DistributeMessageChannel {

    public static final String CHANNEL_CODE = "RabbitMQMessageChannel";

    private AmqpTemplate amqpTemplate;
    private AmqpAdmin amqpAdmin;
    private DistributeMessageProducer messageProducer;
    private DistributeMessageConsumer messageConsumer;
    private DefaultMessageListenerContainer defaultMessageListenerContainer;

    public RabbitMQMessageChannel(DefaultMessageListenerContainer defaultMessageListenerContainer, AmqpTemplate amqpTemplate, AmqpAdmin amqpAdmin) {
        this.defaultMessageListenerContainer = defaultMessageListenerContainer;
        this.amqpTemplate = amqpTemplate;
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    public String getCode() {
        return CHANNEL_CODE;
    }

    @Override
    public DistributeMessageProducer getMessageProducer() {
        if (messageProducer == null)
            messageProducer = new RabbitMQMessageProducer(amqpTemplate, amqpAdmin);
        return messageProducer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {

        if (messageConsumer == null)
            messageConsumer = new RabbitMQMessageConsumer(defaultMessageListenerContainer, amqpAdmin);
        return messageConsumer;
    }

    @Override
    public void start() {

    }

    @Override
    public void close() {
        getMessageConsumer().stop();
    }


}
