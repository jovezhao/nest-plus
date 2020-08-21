package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.context.appservice.UnitOfWorkCommitor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SpringUnitOfWorkCommitor extends UnitOfWorkCommitor {
    @Transactional
    public void commit() {
        super.commit();
    }
}
