package com.wtgroup.demo.demospringboot.constant;

import org.springframework.http.HttpStatus;

/**
 * 返回代码规范表
 *
 * <pre>
 *     约定:
 *      query标识有效的请求(需要统计次数); request表示所有请求
 *
 *     Code: >=0 记录次数, <0 不计次数.
 * </pre>
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/10/22 18:20
 */
public enum StatusCode {

    ERROR(-1, "未知异常"){
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    SUCCESS(0, "SUCCESS"){
        public HttpStatus getHttpStatus() {
            return HttpStatus.OK;
        }
    },

    //--请求者的锅--
    //格式不合法:
    REQUEST_DATA_FORMAT_ERROR(40001,"请求数据格式错误"),
    REQUEST_DATA_PARSE_FAIL(40002,"请求数据格式错误, 解析JSON/XML内容错误"),

    //缺少
    REQUEST_INVALID(41000, "请求无效"),
    REQUEST_NO_REQUEST_DATA(41001,"缺少请求参数"),
    REQUEST_NO_APP_KEY(41002, "缺少请求参数: 'appKey'"),
    REQUEST_NO_TIMESTAMP(41003, "缺少请求参数: 'timestamp'"),
    REQUEST_NO_DATA(41004, "缺少请求参数: 'data'"),

    REQUEST_POST_BODY_SIZE_OVERRUN(41100, "POST请求体大小超限"),

    //没有匹配到数据
    NO_VALID_DATA(44000,"没有匹配"),


    //--服务器的锅--
    //SERVER_DATA_NO_SUPPORT(50010,"服务器数据暂不支持"),
    /**ServerException的缺省状态*/
    SERVER_ERROR(50000,"服务器异常"),
    /**不要暴露给请求方(不要面子呀), 用于提示开发人员*/
    //SERVER_DATA_ERROR(50010,"服务器基础数据异常"),


    //--安全相关--

    /**
     * 当缓存中查到已有的请求标识 => 肯定是重复请求了
     */
    SECURITY_REPEAT_REQUEST(60010, "重复请求异常"),
    /**
     * 当请求标识缓存过期后, 根据时间戳判断的时候, 可能是重复请求, 也可能是网络延迟
     */
    SECURITY_SIMILAR_REPEAT_REQUEST(60011, "疑似重复请求异常"),
    SECURITY_NO_AVAILABLE_RSA_PUBKEY(60100, "没有可用RSA公钥: 请确认是否已向服务方提供RSA公钥"),
    SECURITY_CHECK_SIGN_EXCEPTION(60101, "RSA验签异常"),
    //SECURITY_CHECK_SIGN_INVALIDKEYSPECEXCEPTION(60102,"验签时根据公钥字符串实例化公钥异常: 公钥字符串可能非法"),
    //SECURITY_CHECK_SIGN_INVALIDKEYEXCEPTION(60103,"验签时指定公钥无效"),
    SECURITY_DECRYPT_EXCEPTION(60102, "解密异常"),
    SECURITY_ENCRYPT_EXCEPTION(60103, "加密异常"),
    SECURITY_SIGN_EXCEPTION(60104, "签名异常"),
    SECURITY_SIGN_NO_PASS(60104, "未通过验签"),
    /**仅限加密请求*/
    SECURITY_ENCRYPTION_REQUEST_ONLY(60400, "仅限加密请求"),



    //--权限相关--
    AUTHORITY_INVALID_APP_KEY(70000, "appKey不存在, 请申请"),
    AUTHORITY_INVALID_APP_SECRET(70001, "appSecret不存在"),
    AUTHORITY_INVALID_TOKEN(70002, "token已失效OR不合法"),
    AUTHORITY_QUERY_OVER_LIMIT(70010,"有效访问次数超限"),
    AUTHORITY_NO_USER(70040,"非法接入方"),
    AUTHORITY_WRONG_PASSWORD(70041,"密码错误"),

    //--注册登录--
    REGISTRY_DUPLICATE_USER(80000,"用户名已存在"),

    ;


    private int code;
    private String desc;

    StatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public int code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public HttpStatus getHttpStatus() {
        throw new AbstractMethodError();
    }
}
