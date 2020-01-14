package com.zhaofujun.nest.demo.infrastructure.repository;

import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.core.EntityFactory;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.Repository;
import com.zhaofujun.nest.demo.domain.relating.Classroom;
import com.zhaofujun.nest.demo.domain.relating.Teacher;
import com.zhaofujun.nest.demo.infrastructure.persistence.ClassroomDmo;
import com.zhaofujun.nest.demo.infrastructure.persistence.mapper.ClassroomDmoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClassroomRepository implements Repository<Classroom> {

    @Autowired
    private ClassroomDmoMapper dmoMapper;

    @Override
    public Class<Classroom> getEntityClass() {
        return Classroom.class;
    }

    @Override
    public Classroom getEntityById(Identifier identifier, EntityLoader<Classroom> entityLoader) {
        LongIdentifier longIdentifier = (LongIdentifier) identifier;
        ClassroomDmo classroomDmo = dmoMapper.selectById(longIdentifier.getId());
        if (classroomDmo == null) return null;
        Classroom classroom = entityLoader.create(identifier);
        classroom.setName(classroomDmo.getName());

        classroom.setTeacher(EntityFactory.load(Teacher.class, LongIdentifier.valueOf(classroomDmo.getTeacherId())));

        return classroom;
    }

    @Override
    public void insert(Classroom classroom) {
        if (classroom == null) return;
        ClassroomDmo dmo = toDmo(classroom);
        dmoMapper.insert(dmo);
    }

    private ClassroomDmo toDmo(Classroom classroom) {

        ClassroomDmo dmo = new ClassroomDmo();
        dmo.setId(classroom.getId().getId());
        dmo.setName(classroom.getName());
        dmo.setTeacherId(classroom.getTeacher().getId().getId());

        return dmo;
    }

    @Override
    public void update(Classroom classroom) {
        if (classroom == null) return;
        ClassroomDmo dmo = toDmo(classroom);
        dmoMapper.updateById(dmo);
    }

    @Override
    public void delete(Classroom classroom) {
        dmoMapper.deleteById(classroom.getId().getId());
    }
}
