package com.zhaofujun.nest.rabbitmq;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.standard.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;


/**
 *
 **/
public class RabbitMQMessageConsumer extends DistributeMessageConsumer implements ReceivedMessageHandler {

    private Logger logger = LoggerFactory.getLogger(RabbitMQMessageConsumer.class);
    private AmqpAdmin amqpAdmin;
    private DefaultMessageListenerContainer defaultMessageListenerContainer;


    public RabbitMQMessageConsumer(DefaultMessageListenerContainer defaultMessageListenerContainer, AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
        this.defaultMessageListenerContainer = defaultMessageListenerContainer;
        this.defaultMessageListenerContainer.setReceivedMessageHandler(this);
    }

    @Override
    public void subscribe(EventHandler eventHandler) {

        Queue queue = new Queue(eventHandler.getClass().getSimpleName());
        amqpAdmin.declareQueue(queue);
        FanoutExchange fanoutExchange = new FanoutExchange(eventHandler.getEventCode());
        amqpAdmin.declareExchange(fanoutExchange);
        Binding binding = BindingBuilder.bind(queue)
                .to(fanoutExchange);
        amqpAdmin.declareBinding(binding);

        defaultMessageListenerContainer.addHandler(queue.getName(), eventHandler);

    }


    @Override
    protected void onEnds(EventHandler eventHandler, Object context) {

    }

    @Override
    public void stop() {
        defaultMessageListenerContainer.stop();
    }

    @Override
    public void receivedMessage(MessageInfo messageInfo, EventHandler eventHandler, Object context) {
        onReceivedMessage(messageInfo, eventHandler, context);
    }
}
