package com.zhaofujun.nest.demo.contract;


import com.zhaofujun.nest.standard.EventData;

public class TestEventData extends EventData {

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
