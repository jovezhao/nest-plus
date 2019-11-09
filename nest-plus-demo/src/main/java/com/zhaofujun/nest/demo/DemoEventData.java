package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.context.event.EventData;

public class DemoEventData extends EventData {

    public static final String Code="testEvent";
    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getEventCode() {
        return Code;
    }


    @Override
    public String toString() {
        return "TestEventData{" +
                "data='" + data + '\'' +
                '}';
    }
}
