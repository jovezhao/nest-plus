package com.zhaofujun.nest.activemq;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.ContainerProvider;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;


public class ActiveMQMessageChannel extends DistributeMessageChannel {

    public static final String CHANNEL_CODE = "ActiveMQMessageChannel";

    private ContainerProvider containerProvider;
    private JmsTemplate jmsTemplate;
    private DistributeMessageProducer messageProducer;
    private DistributeMessageConsumer messageConsumer;
    private NestApplication nestApplication;

    public ActiveMQMessageChannel(ContainerProvider containerProvider, JmsTemplate jmsTemplate, NestApplication nestApplication) {
        this.containerProvider = containerProvider;
        this.jmsTemplate = jmsTemplate;
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
            messageProducer = new ActiveMQMessageProducer(jmsTemplate,containerProvider);
        return messageProducer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {

        if (messageConsumer == null)
            messageConsumer = new ActiveMQMessageConsumer(jmsTemplate, this.containerProvider);
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
