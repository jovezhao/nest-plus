//package com.zhaofujun.nest.spring.test.junit;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.zhaofujun.nest.spring.test.models.ClassRoom;
//import com.zhaofujun.nest.spring.test.models.Teacher;
//import org.junit.Test;
//
//public class TestFastjson {
//    @Test
//    public void toJson(){
//        ClassRoom classRoom1 = new ClassRoom();
//        ClassRoom classRoom2 = new ClassRoom();
//        Teacher teacher = new Teacher();
//
//        classRoom1.setName1("className1");
//        classRoom1.setTeacher(teacher);
//
//        classRoom2.setName1("className2");
//
//        teacher.setClassRoom(classRoom1);
//        teacher.setName("teacherName");
//
//        String s = JSON.toJSONString(teacher,SerializerFeature.WriteClassName);
//        String s2 = JSON.toJSONString(classRoom1,SerializerFeature.WriteClassName);
//        Teacher teacher1 = JSON.parseObject(s, Teacher.class);
//        ClassRoom classRoom = JSON.parseObject(s2, ClassRoom.class);
//
//    }
//}
