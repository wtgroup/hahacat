package com.wtgroup.demo.demospringboot.exception;

import com.wtgroup.demo.demospringboot.bean.vo.ResponseVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.annotation.HandlesTypes;

/**
 * 统一异常处理
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-07-01-0:38
 */
@ControllerAdvice
@Log4j2
public class ExceptionHandle {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseVo handle(Exception e) {
        log.info("统一捕获异常开始...");

        if (e instanceof CustomException) {
            //独创秘籍, 异常里就含有已经定义好的vo / 这样方便vo在整个流程中传递
            log.error("[定制异常]", e);

            return ((CustomException) e).getResponseVo();
        }

        log.error("[系统异常]", e);
        return ResponseVo.error(-1, e.getMessage());
    }
}
