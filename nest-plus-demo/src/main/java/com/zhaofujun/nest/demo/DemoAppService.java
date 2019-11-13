package com.zhaofujun.nest.demo;

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
        DemoEventData eventData = new DemoEventData();
        eventData.setData("test event data");
        eventBus.publish(eventData);
    }

}



