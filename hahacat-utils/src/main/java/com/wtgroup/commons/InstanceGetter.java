package com.wtgroup.commons;

/**
 * 通用小工具类
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-24-22:44
 */
public class InstanceGetter<T> {


    /**
     * 根据全类名字符串, 生成对应的实例
     */
    public T getInstanceForName(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<T> clzz = null;
        T instance = null;

        clzz = (Class<T>) Class.forName(className);


        instance = clzz.newInstance();

        return instance;
    }

}
