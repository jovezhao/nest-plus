package com.zhaofujun.nest.aliyun.rocketmq;

import com.aliyun.openservices.ons.api.*;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.core.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Slf4j
public class AliYunRocketMQConsumer extends DistributeMessageConsumer {




    private Properties properties;

    private List<Consumer> consumers=new ArrayList<>();

    public AliYunRocketMQConsumer(BeanFinder beanFinder, Properties properties) {
        super(beanFinder);
        this.properties=properties;
    }

    @Override
    public void stop() {

    }

    @Override
    public void subscribe(EventHandler eventHandler) {

        Consumer consumer = ONSFactory.createConsumer(properties);

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
        this.consumers.add(consumer);
    }
}
