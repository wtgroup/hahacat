package com.wtgroup.demo.demospringboot.aspect;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


/**
 * http请求日志
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-30-21:34
 */
@Aspect
@Component
public class HttpAspect {
    private static final Logger log = LogManager.getLogger(RequestArgsAspect.class);


    @Pointcut("execution(public * com.wtgroup.demo.demospringboot.controller.AopController.*(..))")
    public void log() {
        System.out.println("this is empty method");
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("## before controller");
        Signature signature = joinPoint.getSignature();
        log.info("切点方法签名:{}",signature);
        String name = signature.getName();
        log.info("切点方法名:{}",name);
    }

    @After("log()")
    public void doAfter() {     //around 之后, afterReturning之前 / Controller中即使抛出异常, 这个仍然会得到执行, 打死都要执行的
        System.out.println("after controller");
    }

    @AfterReturning(pointcut = "log()",returning = "responseVo")
    public void doAfterReturning(Object responseVo) {   //after 之后
        System.out.println("## after returning");
        System.out.println("controller 返回的 responseVo: "+responseVo);
    }

}
