package com.zhaofujun.nest.demo.domain;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.LongIdentifier;

public abstract class Teacher extends BaseEntity<LongIdentifier> {
    private String teacherNo;
    private User user;

    public void init(String teacherNo, User user) {
        this.teacherNo = teacherNo;
        this.user = user;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public User getUser() {
        return user;
    }
}
