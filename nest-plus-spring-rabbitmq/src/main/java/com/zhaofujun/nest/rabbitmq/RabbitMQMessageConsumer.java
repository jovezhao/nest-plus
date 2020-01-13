package com.zhaofujun.nest.rabbitmq;

import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.core.EventData;
import com.zhaofujun.nest.core.EventHandler;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.json.JsonCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;


/**
 *
 **/
public class RabbitMQMessageConsumer extends DistributeMessageConsumer {

    private Logger logger = LoggerFactory.getLogger(RabbitMQMessageConsumer.class);
    private AmqpTemplate amqpTemplate;
    private AmqpAdmin amqpAdmin;
    private volatile boolean running = false;


    public RabbitMQMessageConsumer(AmqpTemplate amqpTemplate, AmqpAdmin amqpAdmin, BeanFinder beanFinder) {
        super(beanFinder);
        this.amqpTemplate = amqpTemplate;
        this.amqpAdmin = amqpAdmin;
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

        running = true;
        while (running) {
            for (int i = 0; i < 10; i++) {
                if (running) {

                    Object message = amqpTemplate.receiveAndConvert(eventHandler.getClass().getSimpleName());
                    if (message != null) {

                        MessageInfo messageInfo;
                        try {
                            messageInfo = getMessageConverter().jsonToMessage(message.toString(), eventHandler.getEventDataClass());
                        } catch (Exception ex) {
                            logger.warn("反序列化失败，消息体：" + message, ex);
                            break;
                        }
                        onReceivedMessage(messageInfo, eventHandler, null);

                    }
                }
            }
        }
    }

    @Override
    protected void onFailed(EventHandler eventHandler, Object context, Exception ex) {

    }

    @Override
    protected void onEnds(EventHandler eventHandler, Object context) {

    }

    @Override
    public void stop() {
        this.running = false;
    }
}
