package com.wtgroup.demo.demospringboot.bean.vo;

public enum Status{
    SUCCESS(0,"success"){
        //这里必须是外面有定义过的方法, 不然, 使用者调用不到
        public String abstractFun() {
            return "这是SUCCESS自己实现的abstractFun()";
        }
    },
    ERROR(1,"error");

    private Integer code;
    private String msg;

    Status(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    //-- Test --//
    //枚举也可以玩抽象方法
    //这里定义方法, 但是抛异常
    public String abstractFun() {
        //throw new AbstractMethodError("请实现此方法");
        return "共有的返回值";
    }
}