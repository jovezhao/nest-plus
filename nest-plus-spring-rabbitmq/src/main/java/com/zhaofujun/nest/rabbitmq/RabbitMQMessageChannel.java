package com.zhaofujun.nest.rabbitmq;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.ContainerProvider;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;

/**
 *
 **/
public class RabbitMQMessageChannel extends DistributeMessageChannel {

    public static final String CHANNEL_CODE = "RabbitMQMessageChannel";

    private ContainerProvider containerProvider;
    private AmqpTemplate amqpTemplate;
    private AmqpAdmin amqpAdmin;
    private DistributeMessageProducer messageProducer;
    private DistributeMessageConsumer messageConsumer;
    private NestApplication nestApplication;

    public RabbitMQMessageChannel(ContainerProvider containerProvider, AmqpTemplate amqpTemplate, AmqpAdmin amqpAdmin, NestApplication nestApplication) {
        this.containerProvider = containerProvider;
        this.amqpTemplate = amqpTemplate;
        this.amqpAdmin = amqpAdmin;
        this.nestApplication = nestApplication;

        this.nestApplication.addApplicationListener(new ApplicationListener() {
            @Override
            public void applicationStarted(ApplicationEvent applicationEvent) {
                //应用启动
            }

            @Override
            public void applicationClosed(ApplicationEvent applicationEvent) {
                onClose();
            }
        });
    }

    @Override
    public String getMessageChannelCode() {
        return CHANNEL_CODE;
    }

    @Override
    public DistributeMessageProducer getMessageProducer() {
        if (messageProducer == null)
            messageProducer = new RabbitMQMessageProducer(amqpTemplate, amqpAdmin,containerProvider);
        return messageProducer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {

        if (messageConsumer == null)
            messageConsumer = new RabbitMQMessageConsumer(amqpTemplate, amqpAdmin, this.containerProvider);
        return messageConsumer;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onClose() {
        getMessageConsumer().stop();
    }


}
