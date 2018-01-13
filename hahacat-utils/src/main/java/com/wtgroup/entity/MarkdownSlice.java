package com.wtgroup.entity;


import java.io.Serializable;

/**
 * markdown文件的一个个片段, 如纯文本、代码块、图片、引用、公式...
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-18-19:41
 */
public class MarkdownSlice extends TextSlice {



    /**
     * 文件头部的声明信息
     */
//    public static final String HEAD_STATEMENT = "head_statement";
    //private final String IMAGE = "image";

    private String sliceType;
    private String content;

    public MarkdownSlice() {}

    public MarkdownSlice(String sliceType, String content) {
        this();
//        if (!TYPES.containsTypeName(sliceType)) {
//            throw new RuntimeException("create object 'MarkdownSlice' fail, carsed by there is not slict type " +
//                    "name like " + sliceType);
//        }
        this.sliceType = sliceType;
        this.content = content;
    }

    public String getSliceType() {
        return (String) sliceType;
    }


    public void setSliceType(Serializable sliceType) {
        this.sliceType = (String) sliceType;
    }

    @Override
    public void setContent(Serializable content) {
        this.content = (String) content;
    }

    public String getContent() {
        return content;
    }


    @Override
    public String toString() {
        return content;
    }





}
