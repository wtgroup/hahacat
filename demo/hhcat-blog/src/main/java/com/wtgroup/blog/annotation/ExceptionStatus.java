package com.wtgroup.blog.annotation;

import com.wtgroup.blog.constant.StatusCode;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ExceptionStatus {

    /**
     * 异常状态码
     * @return
     */
    int status() default StatusCode.SUCCESS;

    /**
     * 异常码描述
     * @return
     */
    String desc() default "";


}
