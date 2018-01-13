package com.wtgroup.paser.utils;


import com.wtgroup.constant.Profile;
import com.wtgroup.entity.AbnormalMdSliceTypeMap;
import com.wtgroup.entity.MarkdownSlice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 针对特殊md片段(slice)进行预处理, 如代码块, 直接处理成html代码, 满足个性化需求.
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-18-23:51
 */
public abstract class MarkdownSlicePreprocessor extends SlicePreprocessor {

    // 持有特殊slice集合
    private AbnormalMdSliceTypeMap abtypes = Profile.ABNORMAL_MD_SLICETYPEMAP;

    public MarkdownSlicePreprocessor() {
    }

    public MarkdownSlice preprocess(MarkdownSlice mdSlice) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        String sliceType = (String) mdSlice.getSliceType();
        Method m = matchMethodBySuffix(sliceType);
        if (m == null) {
            return mdSlice;
        } else {
            return (MarkdownSlice) m.invoke(this.getClass().newInstance(), mdSlice);
        }



        /*modify: 根据slice类型名称作为后缀, 匹配对应的处理方法, 对slice做preprocess
        * 如果没有匹配到对应的方法, 则同normal类型处理规则: 原样输出*/
//        // 需要特殊预处理的部分
//        if (abtypes.containsTypeName(sliceType)) {
//            // 根据方法名后缀确定调用的方法
//            Method m = matchMethodBySuffix(sliceType);
//            return (MarkdownSlice) m.invoke(this.getClass().newInstance(), mdSlice);
//        }
//        // 不需特殊预处理的部分, 原样输出
//        return mdSlice;
    }

    /**
     * 批量处理slice
     *
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public List<MarkdownSlice> preprocess(List<MarkdownSlice> slices) throws IllegalAccessException, InstantiationException, InvocationTargetException {

        for (int i = 0; i < slices.size(); i++) {
            preprocess(slices.get(i));
        }
        return slices;
    }

    /**
     * @param textSlice
     */
    public MarkdownSlice preprocessBlock_code(MarkdownSlice textSlice) {
        return textSlice;
    }


}
