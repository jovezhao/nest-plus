package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.context.appservice.ApplicationServiceIntercept;
import com.zhaofujun.nest.context.appservice.MethodInvoker;
import com.zhaofujun.nest.context.appservice.TransactionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class NestAspect {

    @Autowired
    private TransactionManager transactionManager;

    @Around("execution(public * * (..)) && @within(com.zhaofujun.nest.standard.AppService)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodInvoker methodInvoker = new AspectMethodInvoker(joinPoint);
        ApplicationServiceIntercept intercept = new ApplicationServiceIntercept(methodInvoker, transactionManager);
        return intercept.doInvoke();
    }


    class AspectMethodInvoker implements MethodInvoker {
        private ProceedingJoinPoint joinPoint;

        public AspectMethodInvoker(ProceedingJoinPoint joinPoint) {
            this.joinPoint = joinPoint;
        }

        @Override
        public String getMethodName() {
            return joinPoint.getSignature().getName();
        }

        @Override
        public Object invoke() throws Throwable {
            return joinPoint.proceed();
        }

        @Override
        public Class getTargetClass() {
            return joinPoint.getTarget().getClass();
        }

        @Override
        public Object getTarget() {
            return joinPoint.getTarget();
        }
    }
}
