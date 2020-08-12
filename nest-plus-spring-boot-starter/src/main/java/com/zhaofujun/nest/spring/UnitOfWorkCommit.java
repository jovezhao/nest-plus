package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.context.ContextUnitOfWork;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UnitOfWorkCommit {
    @Transactional
    public void commit(ContextUnitOfWork unitOfWork) {
        unitOfWork.commit();
    }
}
