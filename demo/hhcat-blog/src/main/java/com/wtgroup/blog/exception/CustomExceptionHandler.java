package com.wtgroup.blog.exception;

import com.wtgroup.blog.annotation.ExceptionStatus;
import com.wtgroup.blog.utils.WebUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-27-16:16
 */
@Log4j
@Component      //交由spring管理
@ControllerAdvice   //织入controller
public class CustomExceptionHandler {

   // 注入Jackson视图
   @Resource
   private MappingJackson2JsonView mappingJackson2JsonView;

   @ExceptionHandler(Exception.class)     //当出现Exception时, 走这个方法处理
   public ModelAndView exceptionHandle(HttpServletRequest req, HttpServletResponse resp, Exception e,Object handler){
      //打印出异常信息, 供猿人看
      log.error(e);

      ModelAndView mv = new ModelAndView();

      // TODO: 2018/1/27 判断是不是json形式response, 若是, 则要使用Jackson视图

      //获取异常类的ExceptionInfo注解
      ExceptionStatus estatus = AnnotationUtils.findAnnotation(e.getClass(), ExceptionStatus.class);
      //不为null, 则一定是自定义异常类
      if (estatus != null) {
         //获取异常状态码
         mv.addObject("status", estatus.status());
         mv.addObject("desc", estatus.desc());
      }else{   //否则, 则可能是内置异常类, 则统一状态码-1吧, 描述就用message吧, 其异常类也扔进去, 至data中
         mv.addObject("status", -1);
         mv.addObject("desc", e.getMessage());
         mv.addObject("data", e);
      }

      if (WebUtil.isAjaxRequest(req)){ //待增加, 若处理器有@ResponseBody注解也用Jackson视图
         mv.setView(mappingJackson2JsonView);
      } else {
         mv.setViewName("50x");
      }
      return mv;
   }
}
