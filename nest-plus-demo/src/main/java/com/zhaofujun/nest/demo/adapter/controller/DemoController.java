package com.zhaofujun.nest.demo.adapter.controller;

import com.zhaofujun.nest.demo.application.DemoAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private DemoAppService demoAppService;

    @GetMapping("/create")
    public String create() {
        demoAppService.create();
        return "publish";
    }

    @GetMapping("/change")
    public String change() {
        demoAppService.changeAge();
        return "change";
    }
}
