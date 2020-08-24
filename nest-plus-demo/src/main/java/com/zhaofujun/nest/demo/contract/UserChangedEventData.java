package com.zhaofujun.nest.demo.contract;

import com.zhaofujun.nest.standard.EventData;

public class UserChangedEventData implements EventData {
    public static final String CODE = "User_Changed";

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getEventCode() {
        return CODE;
    }
}
