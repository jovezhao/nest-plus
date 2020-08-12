package com.zhaofujun.nest.spring.test.models;

import com.zhaofujun.nest.core.DomainObject;
import com.zhaofujun.nest.core.ValueObject;

public class Teacher extends ValueObject {
    private String name;
    private DomainObject classRoom;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DomainObject getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(DomainObject classRoom) {
        this.classRoom = classRoom;
    }
}
