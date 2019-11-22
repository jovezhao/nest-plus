package com.zhaofujun.nest.demo.adapter.rest;

import com.zhaofujun.nest.demo.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String create() {
        userService.create();
        return "publish";
    }

    @GetMapping("/change")
    public String change() {
        userService.changeAge();
        return "change";
    }
}
