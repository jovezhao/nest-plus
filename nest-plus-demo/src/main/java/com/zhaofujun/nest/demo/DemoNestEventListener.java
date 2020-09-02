package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import org.springframework.stereotype.Component;


@Component
public class DemoNestEventListener implements ApplicationListener, ServiceContextListener {
    @Override
    public void applicationStarted(ApplicationEvent applicationEvent) {
        System.out.println("applicationStarted");
    }

    @Override
    public void applicationClosed(ApplicationEvent applicationEvent) {
        System.out.println("applicationClosed");
    }

    @Override
    public void serviceCreated(ServiceEvent serviceEvent) {
        System.out.println("serviceCreated");
    }

    @Override
    public void serviceMethodStart(ServiceEvent serviceEvent, String method) {
        System.out.println("serviceMethodStart");
    }

    @Override
    public void serviceMethodEnd(ServiceEvent serviceEvent, String method) {
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
}
