package com.zhaofujun.nest.rocketmq;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;


public class RocketMQMessageChannel extends DistributeMessageChannel {

    public static final String CHANNEL_CODE = "RocketMQMessageChannel";

    private RocketMQTemplate rocketMQTemplate;
    private DistributeMessageProducer messageProducer;
    private DistributeMessageConsumer messageConsumer;
    private RocketMQProperties rocketMQProperties;
    private NestApplication nestApplication;

    public RocketMQMessageChannel( RocketMQProperties rocketMQProperties,RocketMQTemplate rocketMQTemplate, NestApplication nestApplication) {
        this.rocketMQTemplate = rocketMQTemplate;
        this.nestApplication = nestApplication;
        this.rocketMQProperties=rocketMQProperties;

        this.nestApplication.addApplicationListener(new ApplicationListener() {
            @Override
            public void applicationStarted(ApplicationEvent applicationEvent) {
                //应用启动
            }

            @Override
            public void applicationClosed(ApplicationEvent applicationEvent) {
                onClose();
            }
        });
    }

    @Override
    public String getCode() {
        return CHANNEL_CODE;
    }

    @Override
    public DistributeMessageProducer getMessageProducer() {
        if (messageProducer == null)
            messageProducer = new RocketMQMessageProducer(rocketMQTemplate);
        return messageProducer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {

        if (messageConsumer == null)
            messageConsumer = new RocketMQMessageConsumer(rocketMQProperties);
        return messageConsumer;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onClose() {
        getMessageConsumer().stop();
    }


}
