package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.Repository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepository implements Repository<User> {

    Map<Identifier, User> useMap = new HashMap<>();

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getEntityById(Identifier identifier, EntityLoader<User> entityLoader) {
        return useMap.get(identifier);
    }

    @Override
    public void insert(User use) {
        useMap.put(use.getId(), use);
    }

    @Override
    public void update(User use) {
        useMap.put(use.getId(), use);

    }

    @Override
    public void delete(User user) {
        useMap.remove(user.getId());

    }


}
