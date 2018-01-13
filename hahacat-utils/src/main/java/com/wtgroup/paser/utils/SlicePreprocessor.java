package com.wtgroup.paser.utils;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-19-11:28
 */
public abstract class SlicePreprocessor {



    protected Method matchMethodBySuffix(String suffix) {
        Method m = null;
        // 根据方法名后缀确定调用的方法
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            // 当前方法以传入的MarkdownSlice类型后缀, 就是要调用它

            if (StringUtils.endsWithIgnoreCase(method.getName(), suffix)) {
                m = method;
                break;
            }
        }

        return m;
    }
}
