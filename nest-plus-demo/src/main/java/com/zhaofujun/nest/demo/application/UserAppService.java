package com.zhaofujun.nest.demo.application;

import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.nest.context.model.EntityFactory;
import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.demo.contract.UserDto;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.standard.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AppService
//@Component
public class UserAppService {
    @Autowired
    private AutoMapper autoMapper;

    public UserDto create(String name, String tel) {
        User user = EntityFactory.create(User.class, LongIdentifier.valueOf(100L));
        user.init(name, tel);
        User user2 = EntityFactory.create(User.class, LongIdentifier.valueOf(101L));
        user2.init(name, tel);
        return autoMapper.map(user, UserDto.class);
    }

    public UserDto changeTel(String tel) {
        User user = EntityFactory.load(User.class, LongIdentifier.valueOf(100L));
        user.changeTel(tel);
        return autoMapper.map(user, UserDto.class);
    }
}
