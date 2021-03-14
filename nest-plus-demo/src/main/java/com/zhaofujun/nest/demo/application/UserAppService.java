package com.zhaofujun.nest.demo.application;

import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.nest.context.model.EntityFactory;
import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.demo.contract.UserChangedEventData;
import com.zhaofujun.nest.demo.contract.UserDto;
import com.zhaofujun.nest.demo.domain.Address;
import com.zhaofujun.nest.demo.domain.Point;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.standard.AppService;
import com.zhaofujun.nest.standard.EventBus;
import org.springframework.beans.factory.annotation.Autowired;


@AppService
//@Component
public class UserAppService {
    @Autowired
    private AutoMapper autoMapper;
    @Autowired
    private EventBus eventBus;


    public UserDto create(String name, String tel) {
        User user = EntityFactory.create(User.class, LongIdentifier.valueOf(100L));
        user.init(name, tel);
        UserChangedEventData eventData = new UserChangedEventData();
        eventData.setUserName(user.getName());
        eventBus.publish(eventData);
        return autoMapper.map(user, UserDto.class);

    }

    public UserDto changeTel(String tel) {
        User user = EntityFactory.load(User.class, LongIdentifier.valueOf(100L));
        user.changeTel(tel);
        return autoMapper.map(user, UserDto.class);
    }

    public UserDto changeAddress() {
        User user = EntityFactory.load(User.class, LongIdentifier.valueOf(100L));
        Address address = new Address("address", new Point(1, 2));
        user.cacheAddress(address);
        return autoMapper.map(user, UserDto.class);
    }

    public void publish() {
        UserChangedEventData eventData = new UserChangedEventData();
        eventData.setUserName("username");
        System.out.println("发送:"+System.currentTimeMillis());
        eventBus.publish(eventData, 5);
    }
}
