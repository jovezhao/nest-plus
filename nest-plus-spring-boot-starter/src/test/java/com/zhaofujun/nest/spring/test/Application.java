package com.zhaofujun.nest.spring.test;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.ContainerProvider;
import com.zhaofujun.nest.core.DomainObject;
import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import com.zhaofujun.nest.json.JsonCreator;
import com.zhaofujun.nest.spring.test.appservices.TestAppservices;
import com.zhaofujun.nest.spring.test.models.ClassRoom;
import com.zhaofujun.nest.spring.test.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;


@SpringBootApplication
public class Application implements CommandLineRunner {


    //设置
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private EventBus eventBus;
    @Autowired
    private TestAppservices testAppservices;

    @Autowired
    ContainerProvider containerProvider;

    @Autowired
    private NestApplication nestApplication;

    public void run() {
//        eventBus.autoRegister();
//        testAppservices.createUser("111", "pwd");
        ClassRoom classRoom1 = new ClassRoom();
        ClassRoom classRoom2 = new ClassRoom();
        Teacher teacher = new Teacher();

        classRoom1.setName("className1");
        ArrayList<DomainObject> teacherList = new ArrayList<>();
        teacherList.add(teacher);
        classRoom1.setTeacher(teacherList);

        classRoom2.setName("className2");

        teacher.setClassRoom(classRoom2);
        teacher.setName("teacherName");

        String s = jsonCreator.toJsonString(classRoom1);
        String s1 = jsonCreator.toJsonString(teacher);
        ClassRoom classRoom = jsonCreator.toObj(s, ClassRoom.class);
    }

    @Autowired
    private JsonCreator jsonCreator;

    @Override
    public void run(String... args) throws Exception {


        nestApplication.addApplicationListener(new ApplicationListener() {
            @Override
            public void applicationStarted(ApplicationEvent applicationEvent) {
                System.out.println("应用启动");
            }

            @Override
            public void applicationClosed(ApplicationEvent applicationEvent) {
                System.out.println("应用停止");

            }
        });
        nestApplication.addServiceContextListener(new ServiceContextListener() {
            @Override
            public void serviceCreated(ServiceEvent serviceEvent) {
                System.out.println("serviceCreated");
            }

            @Override
            public void serviceMethodStart(ServiceEvent serviceEvent, Method method) {
                System.out.println("serviceMethodStart");

            }

            @Override
            public void serviceMethodEnd(ServiceEvent serviceEvent, Method method) {
                System.out.println("serviceMethodEnd");

            }

            @Override
            public void beforeCommit(ServiceEvent serviceEvent) {
                System.out.println("beforeCommit");

            }

            @Override
            public void committed(ServiceEvent serviceEvent) {
                System.out.println("committed");

            }

            @Override
            public void serviceEnd(ServiceEvent serviceEvent) {
                System.out.println("serviceEnd");

            }
        });
        nestApplication.start();
        run();
    }
}


