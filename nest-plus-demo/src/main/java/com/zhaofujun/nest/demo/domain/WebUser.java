package com.zhaofujun.nest.demo.domain;

public class WebUser extends User {
    private int webId;
    private Order order;

    public int getWebId() {
        return webId;
    }

    public void newWebId(int webId) {
        this.webId = webId;
    }


}
