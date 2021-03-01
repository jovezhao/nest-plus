package com.zhaofujun.nest.rabbitmq;


import com.zhaofujun.nest.standard.EventHandler;

public interface ReceivedMessageHandler {
    void receivedMessage(String messageText, EventHandler eventHandler, Object context);
}