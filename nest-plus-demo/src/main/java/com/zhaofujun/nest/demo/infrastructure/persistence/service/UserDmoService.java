package com.zhaofujun.nest.demo.infrastructure.persistence.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaofujun.nest.demo.infrastructure.persistence.UserDmo;
import com.zhaofujun.nest.demo.infrastructure.persistence.mapper.UserDmoMapper;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collection;

@Component
public class UserDmoService extends ServiceImpl<UserDmoMapper, UserDmo> implements IUserDmoService {
//
//    @Autowired
//    private DataSource dataSource;
//
//
//
//
//    @Override
//    public boolean saveBatch(Collection<UserDmo> entityList, int batchSize) {
//        try {
//            return super.saveBatch(entityList, batchSize);
//        } catch (RuntimeException ex) {
//            MyBatisExceptionTranslator myBatisExceptionTranslator = new MyBatisExceptionTranslator(dataSource, false);
//
//            DataAccessException dataAccessException = myBatisExceptionTranslator.translateExceptionIfPossible(ex);
//            throw dataAccessException;
//        }
//    }
}