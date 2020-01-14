package com.zhaofujun.nest.demo.domain.relating;

import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.core.BaseEntity;

public class Teacher extends BaseEntity<LongIdentifier> {
    private transient Classroom classroom;
    private String name;

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
