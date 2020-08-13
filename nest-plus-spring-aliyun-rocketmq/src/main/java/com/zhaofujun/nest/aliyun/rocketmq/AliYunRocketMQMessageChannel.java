package com.zhaofujun.nest.aliyun.rocketmq;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;

public class AliYunRocketMQMessageChannel extends DistributeMessageChannel {

    public static final String CHANNEL_CODE = "AliYunRocketMQMessageChannel";

    private AliYunRocketMQProducer aliYunRocketMQProducer;

    private AliYunRocketMQConsumer aliYunRocketMQConsumer;

    private NestApplication nestApplication;


    public AliYunRocketMQMessageChannel(AliYunRocketMQProducer aliYunRocketMQProducer, AliYunRocketMQConsumer aliYunRocketMQConsumer, NestApplication nestApplication) {
        this.aliYunRocketMQProducer = aliYunRocketMQProducer;
        this.aliYunRocketMQConsumer = aliYunRocketMQConsumer;
        this.nestApplication = nestApplication;
        this.nestApplication.addApplicationListener(new ApplicationListener() {
            @Override
            public void applicationStarted(ApplicationEvent applicationEvent) {

            }

            @Override
            public void applicationClosed(ApplicationEvent applicationEvent) {

            }
        });
    }

    @Override
    public String getMessageChannelCode() {
        return CHANNEL_CODE;
    }

    @Override
    public DistributeMessageProducer getMessageProducer() {
        return aliYunRocketMQProducer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {
        return aliYunRocketMQConsumer;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onClose() {

    }
}
