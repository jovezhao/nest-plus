package com.zhaofujun.nest.rocketmq;

import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

public class RocketMQMessageProducer extends DistributeMessageProducer {

    private RocketMQTemplate rocketMQTemplate;

    public RocketMQMessageProducer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @Override
    public void commit(String messageGroup, MessageInfo messageInfo) {
        String json = getMessageConverter().messageToString(messageInfo);
        rocketMQTemplate.convertAndSend(messageGroup, json);

    }

    private MessageConverter getMessageConverter() {
        return MessageConverterFactory.create();
    }


}
