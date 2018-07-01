package com.wtgroup.demo.demospringboot.exception;

import com.wtgroup.demo.demospringboot.bean.vo.ResponseVo;
import com.wtgroup.demo.demospringboot.bean.vo.Status;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-07-01-0:51
 */
public class CustomException extends Exception{
    //独创: 宜昌里包含vo
   private ResponseVo responseVo;


    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
        //信息赋给vo
        responseVo.addMsg(message);
    }

    public CustomException(Integer code, String message) {
        this(message);
        responseVo.setCode(code);
    }

    //再定制一个构造
    public CustomException(Status status) {
        this(status.getCode(), status.getMsg());
    }

    //再独创一个, 可以传入响应结果包装类
    public CustomException(ResponseVo vo) {
        this.responseVo = vo;
    }

    public ResponseVo getResponseVo() {
        return responseVo;
    }

    public void setResponseVo(ResponseVo responseVo) {
        this.responseVo = responseVo;
    }
}
