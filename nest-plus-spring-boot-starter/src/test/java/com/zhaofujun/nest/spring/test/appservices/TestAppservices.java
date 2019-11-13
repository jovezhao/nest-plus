package com.zhaofujun.nest.spring.test.appservices;

import com.zhaofujun.nest.core.EntityFactory;
import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.context.loader.ConstructEntityLoader;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.context.loader.RepositoryEntityLoader;
import com.zhaofujun.nest.spring.AppService;
import com.zhaofujun.nest.spring.test.models.PasswordChangedEventData;
import com.zhaofujun.nest.spring.test.models.User;
import org.springframework.beans.factory.annotation.Autowired;

@AppService
public class TestAppservices {
    @Autowired
    EventBus eventBus;


    public void changPwd(String userName, String newPwd) {
        User user = EntityFactory.load(User.class, StringIdentifier.valueOf(userName));
        user.changPwd(newPwd);
//        EventBus eventBus = new EventBus(beanFinder);

        PasswordChangedEventData eventObject = new PasswordChangedEventData("newpwd", "oldpwd", "111");

        eventBus.publish(eventObject);
    }


    public void createUser(String usrName, String pwd) {
        User user = EntityFactory.create(User.class, StringIdentifier.valueOf(usrName));
        user.changPwd(pwd);


        PasswordChangedEventData eventObject = new PasswordChangedEventData("newpwd", "oldpwd", "111");
//
        eventBus.publish(eventObject);
    }
}

