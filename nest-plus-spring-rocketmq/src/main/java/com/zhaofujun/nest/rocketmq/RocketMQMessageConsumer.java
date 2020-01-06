package com.zhaofujun.nest.rocketmq;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.core.EventHandler;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
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

    public RocketMQMessageConsumer(RocketMQProperties rocketMQProperties, BeanFinder beanFinder) {
        super(beanFinder);
        this.rocketMQProperties = rocketMQProperties;
    }

    @Override
    public void subscribe(EventHandler eventHandler) {

        try {
            DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer(eventHandler.getClass().getSimpleName());

            mqPushConsumer.setNamesrvAddr(rocketMQProperties.getNameServer());
            mqPushConsumer.subscribe(eventHandler.getEventCode(), "");
            mqPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    msgs.forEach(p -> {
                        String messageText = new String(p.getBody(), Charset.forName("UTF-8"));
                        MessageInfo messageInfo;
                        try {
                            messageInfo = getMessageConverter().jsonToMessage(messageText, eventHandler.getEventDataClass());
                        } catch (Exception ex) {
                            logger.warn("反序列化失败，消息体：" + messageText, ex);
                            return;
                        }
                        onReceivedMessage(messageInfo, eventHandler, null);
                    });

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
