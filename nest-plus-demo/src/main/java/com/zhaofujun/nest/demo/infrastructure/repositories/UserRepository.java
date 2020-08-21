package com.zhaofujun.nest.demo.infrastructure.repositories;

import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.demo.infrastructure.repositories.dao.UserDao;
import com.zhaofujun.nest.demo.infrastructure.repositories.dao.po.UserPO;
import com.zhaofujun.nest.standard.EntityLoader;
import com.zhaofujun.nest.standard.Identifier;
import com.zhaofujun.nest.standard.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepository implements Repository<User> {
    @Autowired
    private AutoMapper autoMapper;

    @Autowired
    private UserDao userDao;

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getEntityById(Identifier identifier, EntityLoader<User> entityLoader) {
        LongIdentifier id = (LongIdentifier) identifier;
        UserPO userPO = userDao.getOne(id.getId());
        if (userPO == null) return null;
        User user = entityLoader.create(identifier);
        autoMapper.map(userPO, user);
        return user;
    }

    @Override
    public void insert(User user) {
//        if (user.getId().getId().equals(101L)) throw new RuntimeException("发生异常");
        if (user == null) return;
        UserPO userPO = autoMapper.map(user, UserPO.class);
        userDao.save(userPO);
    }

    @Override
    public void update(User user) {
        if (user == null) return;
        UserPO userPO = autoMapper.map(user, UserPO.class);
        userDao.save(userPO);
    }

    @Override
    public void delete(User user) {
        if (user != null)
            userDao.deleteById(user.getId().getId());
    }
}
