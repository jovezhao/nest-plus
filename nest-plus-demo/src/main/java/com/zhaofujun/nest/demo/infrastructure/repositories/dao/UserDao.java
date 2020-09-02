package com.zhaofujun.nest.demo.infrastructure.repositories.dao;

import com.zhaofujun.nest.demo.infrastructure.repositories.dao.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO> {

}
