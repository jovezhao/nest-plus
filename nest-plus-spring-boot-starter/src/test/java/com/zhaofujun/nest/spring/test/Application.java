package com.zhaofujun.nest.spring.test;

import com.zhaofujun.nest.spring.test.application.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);

    }

    @Autowired
    private UserApplicationService userService;

    @Override
    public void run(String... args) throws Exception {

//        NestApplication application = NestApplication.current();
//        application.start();
////        application.getRepositoryManager().addRepository(new UserRepository());
//        UserApplicationService userService = application.createApplicationService(UserApplicationService.class);
        userService.create();

//        long s = System.currentTimeMillis();
//        for (int i = 0; i < 1000; i++) {
////            userService.create();
        userService.change(1);
////            userService.change(2);
////            userService.change(2);
//            userService.change(i);
//        }
//        Long e = System.currentTimeMillis();
//        System.out.println("耗时：" + (e - s));
//        application.close();
        System.out.println("end");
//        String js="{\"CGLIB$BOUND\":true,\"CGLIB$CALLBACK_0\":null,\"name\":\"name1\",\"age\":11,\"id\":{\"id\":10},\"_version\":0,\"__type__\":\"com.zhaofujun.nest.test.domain.User\"}";
//        JsonCreator jsonCreator=new JsonCreator();
//        User user = jsonCreator.toObj(js, User.class);
    }
}
