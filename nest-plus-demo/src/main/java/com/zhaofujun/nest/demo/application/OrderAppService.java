package com.zhaofujun.nest.demo.application;

import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.core.EntityFactory;
import com.zhaofujun.nest.demo.contract.OrderService;
import com.zhaofujun.nest.demo.contract.UserService;
import com.zhaofujun.nest.demo.domain.Order;
import com.zhaofujun.nest.demo.domain.WebUser;
import com.zhaofujun.nest.spring.AppService;
import org.springframework.beans.factory.annotation.Autowired;

@AppService
public class OrderAppService implements OrderService {

    @Autowired
    private UserService userService;

    public void create() {
        Order order = EntityFactory.create(Order.class, StringIdentifier.valueOf("1111"));
        WebUser user = EntityFactory.create(WebUser.class, StringIdentifier.valueOf("1111"));
        user.newWebId(1);
        user.init("name", 50);
        order.setSeller(user);
    }

    public void get(){
        Order order=EntityFactory.load(Order.class,StringIdentifier.valueOf("1111"));
        order.getSeller().changeAge(80);
        userService.create();
    }
}
