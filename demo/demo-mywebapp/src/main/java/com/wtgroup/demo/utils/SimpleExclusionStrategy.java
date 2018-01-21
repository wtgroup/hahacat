package com.wtgroup.demo.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * ExclusionStrategy的实现类,简化操作.
 * 在Object和json相互转换(序列化和反序列化)过程中, 设置字段的过滤规则.
 * 注: 添加排除和添加包含两类方法, 二选一.
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-10-17:36
 */
public class SimpleExclusionStrategy implements ExclusionStrategy {

    private String[] excFiedNames;
    private Class[] excClasses;
    private String[] incFiedNames;
    private Class[] incClasses;

    /**
     * 根据字段名字排除
     *
     * @param filedNames
     */
    public SimpleExclusionStrategy addExcludesByName(String... filedNames) {
        if (incFiedNames != null || incClasses != null) {
            throw new RuntimeException("'addExcludes*'和'addIncludes*'两类方法, 只能二选一!");
        }
        this.excFiedNames = filedNames;
        return this;
    }

    public SimpleExclusionStrategy addExcludesByClass(Class... classes) {
        if (incFiedNames != null || incClasses != null) {
            throw new RuntimeException("'addExcludes*'和'addIncludes*'两类方法, 只能二选一!");
        }

        this.excClasses = classes;
        return this;
    }

    public SimpleExclusionStrategy addIncludesByName(String... filedNames) {
        if (excFiedNames != null || excClasses != null) {
            throw new RuntimeException("'addExcludes*'和'addIncludes*'两类方法, 只能二选一!");
        }

        this.incFiedNames = filedNames;
        return this;
    }

    public SimpleExclusionStrategy addIncludesByClass(Class... classes) {
        if (excFiedNames != null || excClasses != null) {
            throw new RuntimeException("'addExcludes*'和'addIncludes*'两类方法, 只能二选一!");
        }
        this.incClasses = classes;
        return this;
    }


    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        //既没有设置排除,也没有设置包含, 则缺省不排除
        // 定义排除规则
        if (excFiedNames != null) {
            for (String excFiedName : excFiedNames) {
                if (excFiedName.equals(fieldAttributes.getName())) {
                    return true;
                }
            }
            return false;
        }

        // 定义包含规则
        if (incFiedNames != null) {
            for (String incFiedName : incFiedNames) {
                if (incFiedName.equals(fieldAttributes.getName())) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    // toJson(o)时,最先掉用这个方法,传入的就是o本身.
    // 随后才会传入属性的class值
    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        // 定义排除规则
        if (excClasses != null) {
            for (Class c : excClasses) {
                if (c == aClass) {
                    return true;
                }
            }
            return false;
        }

        // 定义包含规则
        if (incClasses != null) {
            for (Class c : excClasses) {
                if (c == aClass) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
