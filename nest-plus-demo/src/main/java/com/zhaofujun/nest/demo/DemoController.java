package com.zhaofujun.nest.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private DemoAppService demoAppService;

    @GetMapping("/publish")
    public String publish() {
        demoAppService.doSomething();
        return "publish";
    }

    @GetMapping("/change")
    public String change() {
        demoAppService.changeAge();
        return "change";
    }
}
