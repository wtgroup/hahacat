package com.wtgroup.blog.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-27-17:43
 */
public class WebUtil {
    
    /**
     * 判断是否为json请求/ajax
     */
    public static boolean isAjaxRequest(HttpServletRequest request){
        String requestType = request.getHeader("X-Requested-With");
        String requestURI = request.getRequestURI();
        if ((requestType != null && "XMLHttpRequest".equals(requestType)) ||
                requestURI.endsWith(".json")) {
            return true;
        }
        return false;
    }
    
}
