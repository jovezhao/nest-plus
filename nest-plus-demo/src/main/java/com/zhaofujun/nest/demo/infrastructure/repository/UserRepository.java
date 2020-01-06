package com.zhaofujun.nest.demo.infrastructure.repository;

import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.nest.context.model.EntityExistedException;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.Repository;
import com.zhaofujun.nest.demo.domain.AppUser;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.demo.domain.WebUser;
import com.zhaofujun.nest.demo.infrastructure.persistence.UserDmo;
import com.zhaofujun.nest.demo.infrastructure.persistence.service.IUserDmoService;
import com.zhaofujun.nest.demo.infrastructure.persistence.mapper.UserDmoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepository implements Repository<User> {

    @Autowired
    private UserDmoMapper userDmoMapper;

    @Autowired
    private AutoMapper autoMapper;


    @Autowired
    private IUserDmoService userDmoService;

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getEntityById(Identifier identifier, EntityLoader<User> entityLoader) {

        UserDmo userDmo = userDmoMapper.selectById(identifier.toValue());

        if (userDmo == null) return null;

        User user = null;
        if (userDmo.getUserType() == UserDmo.APP_USER)
            user = entityLoader.create(AppUser.class, identifier);
        else
            user = entityLoader.create(WebUser.class, identifier);

        autoMapper.map(userDmo, user);
        return user;
    }


    @Override
    public void insert(User use) {

        UserDmo userDmo = autoMapper.map(use, UserDmo.class);

        if (use instanceof AppUser)
            userDmo.setUserType(UserDmo.APP_USER);
        else
            userDmo.setUserType(UserDmo.WEB_USER);

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

        if (use instanceof AppUser)
            userDmo.setUserType(UserDmo.APP_USER);
        else
            userDmo.setUserType(UserDmo.WEB_USER);
        userDmoMapper.updateById(userDmo);
    }

    @Override
    public void delete(User user) {
        userDmoMapper.deleteById(user.getId().getId());

    }

//    @Override
//    public void batchInsert(List<User> users) {
//
//
//        List<UserDmo> userDmoList = users.stream().map(p -> autoMapper.map(p, UserDmo.class)).collect(Collectors.toList());
//
//        try {
//            userDmoService.saveBatch(userDmoList);
//        } catch (DuplicateKeyException ex) {
//            throw new EntityExistedException("用户已经存在") {
//            };
//        }
//    }
}
