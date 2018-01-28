package com.wtgroup.blog.exception;

import com.wtgroup.blog.annotation.ExceptionStatus;
import com.wtgroup.blog.constant.StatusCode;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-27-21:58
 */
@ExceptionStatus(status = StatusCode.INVALID_AUTH,desc = "权限不足")
public class CustomSecurityException extends RuntimeException {
}
