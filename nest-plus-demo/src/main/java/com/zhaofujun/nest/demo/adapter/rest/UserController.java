package com.zhaofujun.nest.demo.adapter.rest;

import com.zhaofujun.nest.demo.application.UserAppService;
import com.zhaofujun.nest.demo.contract.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserAppService userAppService;

    @PostMapping("/create")
    public UserDto create() {
        return userAppService.create("name", "tel");
    }

    @PostMapping("/update")
    public UserDto udpate() {
        return userAppService.changeAddress();
    }
}
