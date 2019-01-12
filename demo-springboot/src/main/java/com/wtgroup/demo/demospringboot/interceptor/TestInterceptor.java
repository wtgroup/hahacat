package com.wtgroup.demo.demospringboot.interceptor;


import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 测试
 *
 * Object handler: Controller方法信息
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/10/23 18:14
 */
public class TestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(">>>TestInterceptor>>>>>>>preHandle()");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println(">>>TestInterceptor>>>>>>>postHandle()");

        /*JSONObject res = new JSONObject();
        res.put("x","xxxxxxxxxxx");
        res.put("y","yyyyyyyyyyy");
        //已经调用过了getOutputStream
        PrintWriter out = response.getWriter();
        out.append(res.toString());*/




    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println(">>>TestInterceptor>>>>>>>afterCompletion()");
        /*
        * 静态资源时, 这里会报错,  => 拦截器一定不能拦截静态资源*/
//        HandlerMethod handlerMethod = (HandlerMethod)handler;
//        HandlerMethod rfm = handlerMethod.getResolvedFromHandlerMethod();


        /*JSONObject res = new JSONObject();
        res.put("x","xxxxxxxxxxx");
        res.put("y","yyyyyyyyyyy");
        //已经调用过了getOutputStream
        PrintWriter out = response.getWriter();
        out.append(res.toString());*/


    }
}
