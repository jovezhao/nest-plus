package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.demo.application.UserAppService;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import com.zhaofujun.nest.utils.LockUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DemoNestEventListener implements ApplicationListener, ServiceContextListener {

    @Autowired
    private UserAppService appService;

    @Override
    public void applicationStarted(ApplicationEvent applicationEvent) {
        System.out.println("applicationStarted");
        appService.publish();
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

class RunClass implements Runnable {
    @Override
    public void run() {
        for (int j = 0; j < 100; j++) {
            LockUtils.runByLock("aaaa", () -> {
                DemoApplication.i[0]++;
                System.out.println(Thread.currentThread().getName() + ": " + DemoApplication.i[0]);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
