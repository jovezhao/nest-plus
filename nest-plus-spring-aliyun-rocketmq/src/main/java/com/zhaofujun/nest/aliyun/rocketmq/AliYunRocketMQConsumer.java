package com.zhaofujun.nest.aliyun.rocketmq;

import com.aliyun.openservices.ons.api.*;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.core.EventHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;


@Slf4j
public class AliYunRocketMQConsumer extends DistributeMessageConsumer {


    private Consumer consumer;

    public AliYunRocketMQConsumer(BeanFinder beanFinder, Consumer consumer) {
        super(beanFinder);
        this.consumer = consumer;
    }

    @Override
    public void stop() {

    }

    @Override
    public void subscribe(EventHandler eventHandler) {
        consumer.subscribe(eventHandler.getEventCode(), "*", new MessageListener() {
            @Override
            public Action consume(Message message, ConsumeContext context) {
                byte[] body = message.getBody();
                String json=new String(body, Charset.forName("UTF-8"));
                MessageInfo messageInfo = getMessageConverter().jsonToMessage(json, eventHandler.getEventDataClass());
                try{
                    onReceivedMessage(messageInfo,eventHandler,null);
                    return Action.CommitMessage;
                }catch (Exception e){
                    log.error("消息:{}消费失败,失败原因为:"+messageInfo,e.getMessage());
                   return Action.ReconsumeLater;
                }
            }
        });
        consumer.start();
    }
}
