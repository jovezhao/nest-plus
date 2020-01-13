package com.zhaofujun.nest.rabbitmq;

import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.core.EventHandler;

public interface ReceivedMessageHandler {
    void receivedMessage(MessageInfo messageInfo, EventHandler eventHandler, Object context);
}