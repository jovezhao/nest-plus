package com.zhaofujun.nest.rabbitmq;

import com.rabbitmq.client.Channel;
import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.standard.CustomException;
import com.zhaofujun.nest.standard.EventHandler;
import com.zhaofujun.nest.standard.SystemException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.util.ArrayList;
import java.util.List;

public class DefaultMessageListenerContainer extends DirectMessageListenerContainer {
    private List<QueueAndHandler> eventHandlers = new ArrayList<>();
    private ReceivedMessageHandler receivedMessageHandler;


    public DefaultMessageListenerContainer(ConnectionFactory connectionFactory) {
        super.setConnectionFactory(connectionFactory);
        super.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        super.setMessageListener(new ChannelAwareMessageListener() {

            private MessageConverter getMessageConverter(){
                return MessageConverterFactory.create();
            }
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                String queue = message.getMessageProperties().getConsumerQueue();
                EventHandler eventHandler = getEventHandler(queue);
                String messageText = new String(message.getBody());
                MessageInfo messageInfo = null;

                try {
                    messageInfo = getMessageConverter().jsonToMessage(messageText, eventHandler.getEventDataClass());
                } catch (Exception ex) {
                    logger.warn("反序列化失败，消息体：" + messageText, ex);
                    //消息格式不正确，消息做成功消费处理
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                    return;
                }
                try {
                    receivedMessageHandler.receivedMessage(messageInfo, eventHandler, null);
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (CustomException ex) {
                    //发生业务异常，消息做成功消费处理
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (SystemException ex) {
                    //发生系统异常，消息退回通道
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                }
            }
        });
    }

    public void setReceivedMessageHandler(ReceivedMessageHandler receivedMessageHandler) {
        this.receivedMessageHandler = receivedMessageHandler;
    }

    public void addHandler(String queue, EventHandler eventHandler) {
        eventHandlers.add(new QueueAndHandler(queue, eventHandler));
        super.addQueueNames(queue);
    }

    public EventHandler getEventHandler(String queue) {
        QueueAndHandler queueAndHandler = eventHandlers.stream()
                .filter(p -> p.queue.equals(queue))
                .findFirst()
                .orElse(null);
        if (queueAndHandler == null)
            return null;
        else
            return queueAndHandler.getEventHandler();
    }

    class QueueAndHandler {
        private String queue;
        private EventHandler eventHandler;

        public QueueAndHandler(String queue, EventHandler eventHandler) {
            this.queue = queue;
            this.eventHandler = eventHandler;
        }

        public String getQueue() {
            return queue;
        }

        public EventHandler getEventHandler() {
            return eventHandler;
        }
    }
}
