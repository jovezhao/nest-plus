package com.zhaofujun.nest.aliyun.rocketmq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.json.JsonCreator;

public class AliYunRocketMQProducer extends DistributeMessageProducer {


    private Producer producer;

    private JsonCreator jsonCreator;

    public AliYunRocketMQProducer(Producer producer,BeanFinder beanFinder) {
        super(beanFinder);
        this.jsonCreator=new JsonCreator(beanFinder);
        this.producer=producer;
        producer.start();
    }


    @Override
    public void commit(String messageGroup, MessageInfo messageInfo) {
        String json = jsonCreator.toJsonString(messageInfo);
        Message message=new Message();
        message.setBody(json.getBytes());
        message.setTopic(messageGroup);
        producer.send(message);
    }
}
