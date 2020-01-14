package com.zhaofujun.nest.demo.application;

import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.core.BaseEntity;
import com.zhaofujun.nest.core.EntityFactory;
import com.zhaofujun.nest.demo.contract.TeacherService;
import com.zhaofujun.nest.demo.domain.relating.Classroom;
import com.zhaofujun.nest.demo.domain.relating.Teacher;
import com.zhaofujun.nest.spring.AppService;

@AppService
public class TeacherAppService implements TeacherService {

    public void get() {
        Teacher teacher = EntityFactory.createOrLoad(Teacher.class, LongIdentifier.valueOf(111L));
        teacher.getClassroom().setName("name");
    }

    public void create() {
        Teacher teacher = EntityFactory.createOrLoad(Teacher.class, LongIdentifier.valueOf(111L));
        Classroom classroom = EntityFactory.createOrLoad(Classroom.class, LongIdentifier.valueOf(111L));
        teacher.setClassroom(classroom);
        teacher.setName("t1");
        classroom.setTeacher(teacher);
        classroom.setName("c1");
    }
}
