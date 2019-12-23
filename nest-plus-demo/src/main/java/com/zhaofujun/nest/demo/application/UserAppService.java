package com.zhaofujun.nest.demo.application;

import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.core.EntityFactory;
import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.demo.contract.TestEventData;
import com.zhaofujun.nest.demo.contract.UserService;
import com.zhaofujun.nest.demo.domain.AppUser;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.spring.AppService;
import org.springframework.beans.factory.annotation.Autowired;

@AppService
public class UserAppService implements UserService {

    @Autowired
    private EventBus eventBus;


    public void create() {

        AppUser user = EntityFactory.createOrLoad(AppUser.class, StringIdentifier.valueOf("111"));
        user.init("老赵", 500);

        user.changeAge(50);
        user.newAppId(1111);
        TestEventData eventData = new TestEventData();
        eventData.setData("test event data");
        eventBus.publish(eventData);


        User use = EntityFactory.createOrLoad(User.class, StringIdentifier.valueOf("111"));
        use.changeAge(20);
    }

    public void changeAge() {
        User use = EntityFactory.load(User.class, StringIdentifier.valueOf("111"));
        use.changeAge(20);
    }

}



