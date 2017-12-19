package com.wtgroup.paser.utils;


import com.wtgroup.paser.entity.MarkdownSlice;
import com.wtgroup.paser.entity.SliceTypeMap;
import com.wtgroup.paser.entity.TextSlice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 针对特殊md片段(slice)进行预处理, 如代码块, 直接处理成html代码, 瞒着个性化需求.
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-18-23:51
 */
public abstract class MarkdownSlicePreprocessor extends SlicePreprocessor{

    // 持有特殊slice集合
    private SliceTypeMap types = MarkdownSlice.TYPES;
    public MarkdownSlicePreprocessor() {}

    public TextSlice preprocess(TextSlice textSlice) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        String sliceType = (String) textSlice.getSliceType();
        // 需要特殊预处理的部分
        if ( types.containsTypeName(sliceType) ) {
            // 根据方法名后缀确定调用的方法
            Method m = matchMethodBySuffix(sliceType);
            return (TextSlice) m.invoke(this.getClass().newInstance(), textSlice);
        }
        // 不需特殊预处理的部分, 原样输出
        return textSlice;
    }

    /**
     * @param textSlice
     */
    public TextSlice preprocessCode(TextSlice textSlice) {
        return textSlice;
    }



}
