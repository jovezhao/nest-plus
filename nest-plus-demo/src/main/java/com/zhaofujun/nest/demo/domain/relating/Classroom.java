package com.zhaofujun.nest.demo.domain.relating;

import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.core.BaseEntity;

public class Classroom extends BaseEntity<LongIdentifier> {
    private String name;
    private Teacher teacher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}

