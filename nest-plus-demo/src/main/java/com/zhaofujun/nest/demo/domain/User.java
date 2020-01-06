package com.zhaofujun.nest.demo.domain;

import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.core.BaseEntity;

public abstract class User extends BaseEntity<StringIdentifier> {
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

    @Override
    public void verify() {
        if (age != 0 && age < 10) {
            throw new EntityVerifyException("年龄太小了");
        }
    }
}
