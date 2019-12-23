package com.zhaofujun.nest.demo.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("users")
public class UserDmo {

    public static final int APP_USER = 1;
    public static final int WEB_USER = 2;


    private String id;
    private String name;
    private int age;
    private int userType;
    private int appId;
    private int webId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getWebId() {
        return webId;
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }
}
