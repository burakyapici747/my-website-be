package com.blog.mywebsite.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Around("execution(* com.blog.mywebsite.service.*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}