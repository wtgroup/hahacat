package com.wtgroup.demo.demospringboot.bean.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 结果响应包装类
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-07-01-0:11
 */
public class ResponseVo<T> {

    private Integer code;
    //独创: 允许多条消息
    private List<String> msg = new ArrayList<>();
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<String> getMsg() {
        return msg;
    }

    public ResponseVo<T> addMsg(String msg) {
        this.msg.add(msg);
        return this;
    }
    public ResponseVo<T> addMsg(List<String> msgList) {
        for (String m : msgList) {
            addMsg(m);
        }
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseVo<T> setStatus(Status status) {
        this.code = status.getCode();
        this.addMsg(status.getMsg());
        return this;
    }

    public static <T> ResponseVo success(T data) {
        ResponseVo<T> vo = new ResponseVo<>();
        vo.setData(data);
        vo.setStatus(Status.SUCCESS);
        return vo;
    }

    public static <T> ResponseVo error(String msg) {
        return error(null, msg);
    }
    public static <T> ResponseVo error(Integer code,String msg) {
        ResponseVo<T> vo = new ResponseVo<>();
        if (code == null) {
            vo.setCode(1);
        }else{
            vo.setCode(code);
        }
        vo.addMsg(msg);
        return vo;
    }

}




