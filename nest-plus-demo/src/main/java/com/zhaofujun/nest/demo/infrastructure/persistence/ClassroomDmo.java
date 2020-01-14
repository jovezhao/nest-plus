package com.zhaofujun.nest.demo.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("classroom")
public class ClassroomDmo {
    private long id;
    private String name;
    private long teacherId;

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

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }
}
