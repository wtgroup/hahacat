package com.wtgroup.blog.security;

import com.wtgroup.blog.annotation.Security;
import com.wtgroup.blog.exception.CustomSecurityException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-27-21:42
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (o instanceof HandlerMethod) {   //封装方法定义相关的信息,如类,方法,参数等.
            HandlerMethod handlerMethod = (HandlerMethod) o;
            //获取方法的指定类型的注解
            Security security = handlerMethod.getMethodAnnotation(Security.class);
            if (security == null) {
                return true;    //没有security注解, 表明不需要拦截
            }
            // 否则就需要验证是否在登录状态
            // TODO: 2018/1/27 校验是否为登录状态, 否->踢回登录页面

            //<editor-fold desc="test">
            if ("admin".equals(request.getParameter("username"))) {
                return true;
            }
            //</editor-fold>

            //走到这里说明没有验证通过
            throw new CustomSecurityException();
        }

        //未知HandlerMethod时, 即无法获知是否有security, 则当其不需要验证
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
