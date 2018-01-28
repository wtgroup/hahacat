package com.wtgroup.blog.annotation;

import java.lang.annotation.*;

/**
 * 标明是否要做安全校验
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-27-21:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented //注解表明这个注解应该被 javadoc工具记录. 默认情况下,javadoc是不包括注解的.
public @interface Security {
    //用处待定
    String name() default "";

    String type() default "";



}
