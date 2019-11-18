package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.core.EntityFactory;
import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.spring.AppService;
import com.zhaofujun.nest.spring.SpringBeanContainerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AppService
public class DemoAppService {

    @Autowired
    private EventBus eventBus;


    public void doSomething() {

        User user = EntityFactory.create(User.class, StringIdentifier.valueOf("111"));
        user.init("老赵", 10);

        DemoEventData eventData = new DemoEventData();
        eventData.setData("test event data");
        eventBus.publish(eventData);
    }

    public void changeAge() {
        User use = EntityFactory.load(User.class, StringIdentifier.valueOf("111"));
        use.changeAge(20);
    }

}



