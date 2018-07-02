package com.wtgroup.demo.demospringboot.exception;

import com.wtgroup.demo.demospringboot.bean.vo.ResponseVo;
import com.wtgroup.demo.demospringboot.bean.vo.Status;

/**定制请求参数异常
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-07-01-0:50
 */
public class RequestParamException extends CustomException {
    public RequestParamException() {
    }

    public RequestParamException(String message) {
        super(message);
    }

    public RequestParamException(Integer code, String message) {
        super(code, message);
    }

    public RequestParamException(Status status) {
        super(status);
    }

    public RequestParamException(ResponseVo vo) {
        super(vo);
    }
}
