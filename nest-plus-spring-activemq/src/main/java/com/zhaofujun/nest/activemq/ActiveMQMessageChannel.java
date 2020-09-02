package com.zhaofujun.nest.activemq;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import org.springframework.jms.core.JmsTemplate;


public class ActiveMQMessageChannel extends DistributeMessageChannel {

    public static final String CHANNEL_CODE = "ActiveMQMessageChannel";

    private JmsTemplate jmsTemplate;
    private DistributeMessageProducer messageProducer;
    private DistributeMessageConsumer messageConsumer;

    public ActiveMQMessageChannel(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public String getCode() {
        return CHANNEL_CODE;
    }

    @Override
    public DistributeMessageProducer getMessageProducer() {
        if (messageProducer == null)
            messageProducer = new ActiveMQMessageProducer(jmsTemplate);
        return messageProducer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {

        if (messageConsumer == null)
            messageConsumer = new ActiveMQMessageConsumer(jmsTemplate);
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
