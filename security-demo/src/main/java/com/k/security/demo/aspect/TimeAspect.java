package com.k.security.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-02
 * Time: 23:01
 */
@Component
@Aspect
public class TimeAspect {
   /* @Around("within(com.k.security.demo.controller..*)&&@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long st = System.currentTimeMillis();
        System.out.println("around start");
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println("arg="+arg);
        }
        Object result = joinPoint.proceed();
        System.out.println("around end,耗时:" + (System.currentTimeMillis() - st));
        return result;
    }*/
}
