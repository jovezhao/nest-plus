package com.zhaofujun.nest.activemq;

import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.core.EventData;
import com.zhaofujun.nest.core.EventHandler;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.json.JsonCreator;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;


import javax.jms.*;

public class ActiveMQMessageConsumer extends DistributeMessageConsumer {

    private Logger logger = LoggerFactory.getLogger(ActiveMQMessageConsumer.class);
    private JmsTemplate jmsTemplate;
    private MessageConverter messageConverter;
    private volatile boolean running = false;

    public ActiveMQMessageConsumer(JmsTemplate jmsTemplate, BeanFinder beanFinder) {
        super(beanFinder);
        messageConverter = new MessageConverter(beanFinder);
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void subscribe(EventHandler eventHandler) {
        running = true;
        while (running) {
            for (int i = 0; i < 10; i++) {
                if (running) {
                    Queue queue = new ActiveMQQueue("Consumer." + eventHandler.getClass().getSimpleName() + ".VirtualTopic." + eventHandler.getEventCode());
                    Message message = jmsTemplate.receive(queue);
                    TextMessage textMessage = (TextMessage) message;
                    MessageInfo messageInfo;
                    String messageText = "";
                    try {
                        messageText = textMessage.getText();
                    } catch (JMSException ex) {
                        logger.warn("接受message失败:" + messageText, ex);
                        break;
                    }
                    try {
                        messageInfo = messageConverter.fromString(messageText, eventHandler.getEventDataClass());
                    } catch (Exception ex) {
                        logger.warn("反序列化失败，消息体：" + messageText, ex);
                        break;
                    }
                    onReceivedMessage(messageInfo, eventHandler, null);
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
        running = false;
    }
}
