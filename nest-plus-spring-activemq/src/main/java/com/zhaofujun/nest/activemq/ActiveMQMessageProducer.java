package com.zhaofujun.nest.activemq;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

public class ActiveMQMessageProducer extends DistributeMessageProducer {

    private JmsTemplate jmsTemplate;

    public ActiveMQMessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void commit(String messageGroup,String messageId, String messageText) {
        Topic topic = new ActiveMQTopic("VirtualTopic." + messageGroup);

        jmsTemplate.send(topic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(messageText);
            }
        });

    }

    private MessageConverter getMessageConverter() {
        return MessageConverterFactory.create();
    }


}
