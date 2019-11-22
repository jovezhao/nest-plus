package com.zhaofujun.nest.demo.infrastructure.repository;

import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.nest.CustomException;
import com.zhaofujun.nest.context.model.EntityExistedException;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.Repository;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.demo.infrastructure.persistence.UserDmo;
import com.zhaofujun.nest.demo.infrastructure.persistence.mapper.UserDmoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
public class UserRepository implements Repository<User> {

    @Autowired
    private UserDmoMapper userDmoMapper;

    @Autowired
    private AutoMapper autoMapper;

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getEntityById(Identifier identifier, EntityLoader<User> entityLoader) {
        UserDmo userDmo = userDmoMapper.selectById(identifier.toValue());

        if (userDmo == null) return null;
        User user = entityLoader.create(identifier);
        autoMapper.map(userDmo, user);
        return user;
    }


    @Override
    public void insert(User use) {

        UserDmo userDmo = autoMapper.map(use, UserDmo.class);
        try {
            userDmoMapper.insert(userDmo);
        } catch (DuplicateKeyException ex) {
            throw new EntityExistedException("用户已经存在") {
            };
        }
    }

    @Override
    public void update(User use) {
        UserDmo userDmo = autoMapper.map(use, UserDmo.class);

        userDmoMapper.updateById(userDmo);
    }

    @Override
    public void delete(User user) {
        userDmoMapper.deleteById(user.getId().getId());

    }


}
