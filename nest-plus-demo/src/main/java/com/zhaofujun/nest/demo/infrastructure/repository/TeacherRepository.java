package com.zhaofujun.nest.demo.infrastructure.repository;

import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.core.EntityFactory;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.Repository;
import com.zhaofujun.nest.demo.domain.relating.Classroom;
import com.zhaofujun.nest.demo.domain.relating.Teacher;
import com.zhaofujun.nest.demo.infrastructure.persistence.ClassroomDmo;
import com.zhaofujun.nest.demo.infrastructure.persistence.TeacherDmo;
import com.zhaofujun.nest.demo.infrastructure.persistence.mapper.ClassroomDmoMapper;
import com.zhaofujun.nest.demo.infrastructure.persistence.mapper.TeacherDmoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherRepository implements Repository<Teacher> {
    @Autowired
    private TeacherDmoMapper dmoMapper;

    @Override
    public Class<Teacher> getEntityClass() {
        return Teacher.class;
    }

    @Override
    public Teacher getEntityById(Identifier identifier, EntityLoader<Teacher> entityLoader) {
        LongIdentifier longIdentifier = (LongIdentifier) identifier;
        TeacherDmo teacherDmo = dmoMapper.selectById(longIdentifier.getId());
        if (teacherDmo == null) return null;
        Teacher teacher = entityLoader.create(identifier);
        teacher.setName(teacher.getName());

        teacher.setClassroom(EntityFactory.load(Classroom.class, LongIdentifier.valueOf(teacherDmo.getClassroomId())));

        return teacher;
    }

    @Override
    public void insert(Teacher teacher) {
        if (teacher == null) return;
        TeacherDmo dmo = toDmo(teacher);
        dmoMapper.insert(dmo);
    }

    private TeacherDmo toDmo(Teacher teacher) {

        TeacherDmo dmo = new TeacherDmo();
        dmo.setId(teacher.getId().getId());
        dmo.setName(teacher.getName());
        dmo.setClassroomId(teacher.getClassroom().getId().getId());

        return dmo;
    }

    @Override
    public void update(Teacher teacher) {
        if (teacher == null) return;
        TeacherDmo dmo = toDmo(teacher);
        dmoMapper.updateById(dmo);
    }

    @Override
    public void delete(Teacher teacher) {
        dmoMapper.deleteById(teacher.getId().getId());
    }
}
