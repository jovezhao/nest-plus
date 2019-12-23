package com.zhaofujun.nest.activemq;

import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.json.JsonCreator;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class ActiveMQMessageProducer extends DistributeMessageProducer {

    private JmsTemplate jmsTemplate;
    private JsonCreator jsonCreator;

    public ActiveMQMessageProducer(JmsTemplate jmsTemplate, BeanFinder beanFinder) {
        super(beanFinder);
        jsonCreator=new JsonCreator(beanFinder);
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void commit(String messageGroup, MessageInfo messageInfo) {
        Topic topic = new ActiveMQTopic("VirtualTopic." + messageGroup);
        String json = jsonCreator.toJsonString(messageInfo);
        jmsTemplate.send(topic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(json);
            }
        });

    }


}
