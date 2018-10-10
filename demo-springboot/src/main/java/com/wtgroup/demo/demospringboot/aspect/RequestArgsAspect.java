package com.wtgroup.demo.demospringboot.aspect;

import com.wtgroup.demo.demospringboot.bean.vo.ResponseVo;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * http请求参数切面类, 注入统一的响应vo
 * 切点: Map,ResponseVo
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-07-01-20:24
 */
@Aspect
@Component
public class RequestArgsAspect {
    private static final Logger log = LogManager.getLogger(RequestArgsAspect.class);

    @Pointcut("execution(* com.wtgroup.demo.demospringboot.controller.*.*(java.util.Map,com.wtgroup.demo.demospringboot.bean.vo.ResponseVo)) " +
            "&& args(params,responseVo)")       //!类型完全一致才行, 父子类都不行!
    public void point(Map params, ResponseVo responseVo) {   //入参合上面注解要对应

    }


    @Around(value = "point(params, responseVo)")  //, argNames = "params,responseVo"
    public ResponseVo aroundController(ProceedingJoinPoint joinPoint, Map params, ResponseVo responseVo) throws Throwable {
        log.info("## aroundController 开始...");
        if (responseVo==null) {
            responseVo = new ResponseVo();
        }
        responseVo.addMsg("我是aop生成的");

        //执行目标controller方法
        responseVo= (ResponseVo) joinPoint.proceed(joinPoint.getArgs());

        System.out.println("## aroundController结束");
        return responseVo;
    }

}
