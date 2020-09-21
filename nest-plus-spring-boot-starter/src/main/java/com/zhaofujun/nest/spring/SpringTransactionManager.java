package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.context.appservice.TransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SpringTransactionManager implements TransactionManager {
    @Transactional
    public void commit(Runnable runnable) {
        runnable.run();
    }
}
