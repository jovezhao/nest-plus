package com.zhaofujun.nest.spring.test.junit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhaofujun.nest.core.DomainObject;
import com.zhaofujun.nest.json.DomainObjectSerializeContext;
import com.zhaofujun.nest.spring.test.models.ClassRoom;
import com.zhaofujun.nest.spring.test.models.Teacher;
import org.junit.Test;

import java.util.ArrayList;

public class TestGson {
    @Test
    public void toJson() {
        ClassRoom classRoom1 = new ClassRoom();
        Teacher teacher = new Teacher();

        classRoom1.setName("className1");
        ArrayList<DomainObject> teacherList = new ArrayList<>();
        teacherList.add(teacher);
        classRoom1.setTeacher(teacherList);


        teacher.setClassRoom(classRoom1);
        teacher.setName("teacherName");

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new DomainObjectTypeAdapterFactory())
                .setPrettyPrinting()
                .create();

        DomainObjectSerializeContext.clear();
        String s = gson.toJson(classRoom1);
        DomainObjectSerializeContext.clear();

        DomainObjectSerializeContext.clear();
        String s2 = gson.toJson(teacher);
        DomainObjectSerializeContext.clear();
//        String s = "{\n" +
//                "  \"name\": \"className1\",\n" +
//                "  \"teacher\": {\n" +
//                "    \"name\": \"teacherName\",\n" +
//                "    \"__ty1pe__\": \"com.zhaofujun.nest.spring.test.models.Teacher\"\n" +
//                "  },\n" +
//                "  \"__loading\": false,\n" +
//                "  \"__newInstance\": false,\n" +
//                "  \"__changed\": false,\n" +
//                "  \"_version\": 0,\n" +
//                "  \"__type__\": \"com.zhaofujun.nest.spring.test.models.ClassRoom\"\n" +
//                "}";
        DomainObject domainObject = gson.fromJson(s, ClassRoom.class);
    }
}
