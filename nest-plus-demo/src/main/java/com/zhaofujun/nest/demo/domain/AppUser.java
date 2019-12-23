package com.zhaofujun.nest.demo.domain;

public class AppUser extends User {
    private int appId;

    public int getAppId() {
        return appId;
    }

    public void newAppId(int appId) {
        this.appId = appId;
    }
}
