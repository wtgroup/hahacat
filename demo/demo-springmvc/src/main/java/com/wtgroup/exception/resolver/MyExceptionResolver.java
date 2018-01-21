package com.wtgroup.exception.resolver;

import com.wtgroup.exception.exception.MyException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-18-3:03
 */
public class MyExceptionResolver implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        MyException myex;
        if (e instanceof MyException) {
            myex = (MyException) e;
        } else {
            myex = new MyException("未知异常");
        }
        mv.addObject("error",myex.getMsg());

        mv.setViewName("info");

        return mv;
    }
}
