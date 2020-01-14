package com.zhaofujun.nest.demo.adapter.rest;

import com.zhaofujun.nest.demo.application.TeacherAppService;
import com.zhaofujun.nest.demo.contract.OrderService;
import com.zhaofujun.nest.demo.contract.UserService;
import com.zhaofujun.nest.spring.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TeacherAppService teacherAppService;
    @GetMapping("/create")
    public String create() {
        teacherAppService.create();
        return "publish";
    }

    @GetMapping("/get")
    public String get() {
        teacherAppService.get();
        return "get";
    }
}
