package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.core.BaseEntity;

public class User extends BaseEntity<StringIdentifier> {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void init(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void changeAge(int age) {
        this.age = age;
    }
}
