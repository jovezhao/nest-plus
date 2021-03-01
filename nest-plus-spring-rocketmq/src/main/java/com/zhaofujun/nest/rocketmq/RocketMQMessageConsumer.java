package com.zhaofujun.nest.rocketmq;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.standard.CustomException;
import com.zhaofujun.nest.standard.EventHandler;
import com.zhaofujun.nest.standard.SystemException;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class RocketMQMessageConsumer extends DistributeMessageConsumer {

    private Logger logger = LoggerFactory.getLogger(RocketMQMessageConsumer.class);
    private RocketMQProperties rocketMQProperties;
    private List<DefaultMQPushConsumer> consumers = new ArrayList<>();

    public RocketMQMessageConsumer(RocketMQProperties rocketMQProperties) {
        this.rocketMQProperties = rocketMQProperties;
    }

    @Override
    public void subscribe(EventHandler eventHandler) {


        try {
            DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer(eventHandler.getClass().getSimpleName());

            mqPushConsumer.setNamesrvAddr(rocketMQProperties.getNameServer());
            mqPushConsumer.setMessageModel(MessageModel.CLUSTERING);
            mqPushConsumer.subscribe(eventHandler.getEventCode(), "");
            mqPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
                private MessageConverter getMessageConverter() {
                    return MessageConverterFactory.create();
                }

                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                    for (MessageExt p : msgs) {
                        String messageText = new String(p.getBody(), Charset.forName("UTF-8"));

                        try {
                            onReceivedMessage(messageText, eventHandler, null,null);
                        } catch (CustomException ex) {
                            //发生业务异常，消息做成功消费处理
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        } catch (SystemException ex) {
                            //发生系统异常，消息退回通道
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            mqPushConsumer.start();
            consumers.add(mqPushConsumer);
        } catch (MQClientException ex) {
            logger.warn("订阅RocketMQ失败", ex);
        }

    }


    @Override
    public void stop() {
        consumers.forEach(p -> p.shutdown());
    }
}
