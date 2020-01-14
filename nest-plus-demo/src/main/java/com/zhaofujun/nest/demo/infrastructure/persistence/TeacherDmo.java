package com.zhaofujun.nest.demo.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("teachers")
public class TeacherDmo {
    private long id;
    private String name;
    private long classroomId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(long classroomId) {
        this.classroomId = classroomId;
    }
}
