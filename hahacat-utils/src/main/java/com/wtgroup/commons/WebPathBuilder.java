package com.wtgroup.commons;

import org.apache.commons.lang.StringUtils;

/**
 * web路径拼接
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2018-01-05-11:08
 */
public class WebPathBuilder {

    private static final String WEB_PATH_SEPARATOR ="/";


    /**
     * 拼接出相对web路径,形如 'xxx/yyy/zzz/'.
     * @param parts
     * @return
     */
    public static String buildRelativePath(String ... parts) {
        String joinParts = join(parts);
        return joinParts + WEB_PATH_SEPARATOR;
    }


    /**
     * 拼接处绝对web路径. 形如 '/xxx/yyy/zzz/'
     * @param parts
     * @return
     */
    public static String buildAbsolutePath(String ... parts) {
        return WEB_PATH_SEPARATOR + buildRelativePath(parts);
    }


    private static String join(String... parts) {
        // 将所有的part首尾的/去掉, 然后以/连接
        for (String part : parts) {
            part = trim(part, WEB_PATH_SEPARATOR);
        }
        return StringUtils.join(parts, WEB_PATH_SEPARATOR);

    }

    private static String trim(String s, String trimChar) {
        if (s.startsWith(trimChar)) {
            s = s.substring(trimChar.length());
        }
        if (s.endsWith(trimChar)) {
            s = s.substring(0, s.length() - trimChar.length());
        }
        return s;
    }


}
