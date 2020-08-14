package com.zhaofujun.nest.aliyun.rocketmq;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.ons.model.v20190214.OnsTopicCreateRequest;
import com.aliyuncs.ons.model.v20190214.OnsTopicListRequest;
import com.aliyuncs.ons.model.v20190214.OnsTopicListResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.configuration.EventConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class AliYunRocketMQAutoConfiguration {

    @Value("${aliyun.accessKey}")
    private String accessKey;

    @Value("${aliyun.secretKey}")
    private String secretKey;
    @Value("${aliyun.rocketmq.groupId}")
    private String groupId;
    @Value("${aliyun.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${aliyun.rocketmq.instanceId}")
    private String instanceId;

    @Value("${aliyun.rocketmq.regionId}")
    private String regionId;



    @Bean
    public Producer createProducer(){
        Properties properties = new Properties();
        // AccessKeyId 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.AccessKey,accessKey);
        // AccessKeySecret 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.SecretKey,secretKey);
        //设置发送超时时间，单位毫秒。
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置 TCP 接入域名，进入控制台的实例详情页面的 TCP 协议客户端接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);

        properties.put(PropertyKeyConst.GROUP_ID,groupId);
        Producer producer = ONSFactory.createProducer(properties);
        return producer;
    }

    @Bean
    public Consumer createConsumer(){
        Properties properties = new Properties();
        // AccessKeyId 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.AccessKey,accessKey);
        // AccessKeySecret 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.SecretKey,secretKey);
        //设置发送超时时间，单位毫秒。
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置 TCP 接入域名，进入控制台的实例详情页面的 TCP 协议客户端接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);
        properties.put(PropertyKeyConst.GROUP_ID,groupId);
        Consumer consumer = ONSFactory.createConsumer(properties);
        return consumer;

    }

    @Bean
    public IAcsClient createIAcsClient(){
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKey, secretKey);
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    };


    @Bean
    public AliYunRocketMQMessageChannel aliYunRocketMQMessageChannel(Producer producer,
                                                                     Consumer consumer,
                                                                     NestApplication nestApplication,
                                                                     IAcsClient iAcsClient) throws ClientException {
        AliYunRocketMQProducer aliYunRocketMQProducer = new AliYunRocketMQProducer(producer,nestApplication.getBeanFinder());
        AliYunRocketMQConsumer aliYunRocketMQConsumer=new AliYunRocketMQConsumer(nestApplication.getBeanFinder(),consumer);


        Set<EventConfiguration> eventConfigurations = nestApplication.getBeanFinder().getInstances(EventConfiguration.class);
        if(!ObjectUtils.isEmpty(eventConfigurations)){

            /**
             * 获取使用阿里云TOPIC通道的事件
             */
            Set<EventConfiguration> aliyunTopicChannel = eventConfigurations.stream().filter(n -> n.getMessageChannelCode().equals(AliYunRocketMQMessageChannel.CHANNEL_CODE)).collect(Collectors.toSet());
            if(!ObjectUtils.isEmpty(aliyunTopicChannel)){
                //查询阿里云ROCKET_MQ TOPIC列表
                OnsTopicListRequest topicListRequest=new OnsTopicListRequest();
                topicListRequest.setInstanceId(instanceId);
                topicListRequest.setRegionId(regionId);
                OnsTopicListResponse acsResponse = iAcsClient.getAcsResponse(topicListRequest);
                Map<String, OnsTopicListResponse.PublishInfoDo> topicListMap=new HashMap<>();
                if(!ObjectUtils.isEmpty(acsResponse.getData())){
                    topicListMap= acsResponse.getData().stream().collect(Collectors.toMap(OnsTopicListResponse.PublishInfoDo::getTopic, Function.identity(), (k1, k2) -> k1));
                }
                Map<String, OnsTopicListResponse.PublishInfoDo> topicMap=topicListMap;

                aliyunTopicChannel.stream().forEach(n->{

                    //查阅TOPIC是否存在，如果不存在，则创建一个TOPIC
                    OnsTopicListResponse.PublishInfoDo publishInfoDo = topicMap.get(n.getEventCode());
                    if(ObjectUtils.isEmpty(publishInfoDo)){
                        //创建一个TOPIC
                        OnsTopicCreateRequest createRequest=new OnsTopicCreateRequest();
                        createRequest.setTopic(n.getEventCode());
                        createRequest.setInstanceId(instanceId);
                        createRequest.setRemark("自动创建TOPIC:"+n.getEventCode());
                        createRequest.setMessageType(2);
                        try {
                           iAcsClient.getAcsResponse(createRequest);
                        } catch (ClientException e) {
                            log.error("创建阿里云ROCKETMQ的TOPIC:{}失败,原因为:{}",n.getEventCode(),e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
        return new AliYunRocketMQMessageChannel(aliYunRocketMQProducer,aliYunRocketMQConsumer,nestApplication);
    }


}
