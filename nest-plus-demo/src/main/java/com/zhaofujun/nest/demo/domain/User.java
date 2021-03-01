package com.zhaofujun.nest.demo.domain;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.LongIdentifier;

public abstract class User extends BaseEntity<LongIdentifier> {
    private String name;
    private String tel;
    private Address address;

    public void init(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public void changeTel(String tel) {
        this.tel = tel;
    }

    public void cacheAddress(Address address) {
        this.address = address;
    }

}

