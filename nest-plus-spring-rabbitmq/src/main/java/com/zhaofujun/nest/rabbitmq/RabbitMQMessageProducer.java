package com.zhaofujun.nest.rabbitmq;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;


/**
 *
 **/
public class RabbitMQMessageProducer extends DistributeMessageProducer {

    private AmqpTemplate amqpTemplate;
    private AmqpAdmin amqpAdmin;

    public RabbitMQMessageProducer(AmqpTemplate amqpTemplate, AmqpAdmin amqpAdmin) {
        this.amqpTemplate = amqpTemplate;
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    public void commit(String messageGroup, String messageText) {
        amqpAdmin.declareExchange(new FanoutExchange(messageGroup));

        amqpTemplate.convertAndSend(messageGroup, "", messageText);
//        try {
//
//            channel = connection.createChannel();
//            channel.exchangeDeclare(this.exchangeName, this.exchangeType, true, false, null);
//            channel.queueDeclare(messageGroup, false, false, false, null);
//            channel.queueBind(messageGroup, this.exchangeName, this.routingKey);
//            channel.basicPublish(this.exchangeName, this.routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, JsonUtils.toJsonString(messageInfo).getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new SystemException("发送RabbitMQ消息失败", e);
//        }
    }

    private MessageConverter getMessageConverter() {
        return MessageConverterFactory.create();
    }
}
