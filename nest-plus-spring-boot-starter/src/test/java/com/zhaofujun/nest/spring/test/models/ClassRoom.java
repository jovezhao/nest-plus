package com.zhaofujun.nest.spring.test.models;

import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.core.BaseEntity;
import com.zhaofujun.nest.core.DomainObject;
import com.zhaofujun.nest.core.ValueObject;

import java.util.List;

public class ClassRoom extends BaseEntity<LongIdentifier> {
    private String name;
    private List<DomainObject> teacher;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DomainObject> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<DomainObject> teacher) {
        this.teacher = teacher;
    }
}
