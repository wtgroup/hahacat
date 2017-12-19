package com.wtgroup.paser.entity;


import com.wtgroup.paser.entity.AbnormalMdSliceTypeMap;
import com.wtgroup.paser.entity.SliceTypeMap;
import com.wtgroup.paser.entity.TextSlice;

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
     * 无需特殊预处理的正常普通片段
     */
    /*
    * 大部分markdown元素都不需要做特殊处理, 就归为normal一类就好.
    * 后期有需要添加特殊处理的部分在这里扩展枚举的类型*/
    // 定义markdown文件片段的类型
    public static final String NORMAL = "normal";     // 不需要特殊的预处理的部分暂归为这类
    /**
     * 代码块
     */
    //private final String PLAIN = "plain";
    public static final String CODE = "code";

    /**
     * 后期预处理只会对这里面的类型做预处理, 不在这里面的, 就原样输出, 不作处理
     */
    public static final SliceTypeMap TYPES = new AbnormalMdSliceTypeMap();

    /**
     * 文件头部的声明信息
     */
//    public static final String HEAD_STATEMENT = "head_statement";
    //private final String IMAGE = "image";

    private String sliceType;
    private String content;

    public MarkdownSlice() {
        TYPES.put(new SliceTypeMap.TypeEntity(CODE, "```", "```"));
    }

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
