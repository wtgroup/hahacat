package com.wtgroup.utils.pcddata.constant;

import com.wtgroup.utils.pcddata.entity.AbnormalMdSliceTypeMap;
import com.wtgroup.utils.pcddata.entity.SliceTypeMap;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 常量配置类
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-27-22:15
 */
public class Profile {


    /**
     * 全局配置参数
     */
    public static Properties HHCATPROPERTIES;



    /**
     * 无需特殊预处理的正常普通片段
     */
    /*
    * 大部分markdown元素都不需要做特殊处理, 就归为normal一类就好.
    * 后期有需要添加特殊处理的部分在这里扩展枚举的类型*/
    // 定义markdown文件片段的类型
    public static final String NORMAL_SLICE = "normal";     // 不需要特殊的预处理的部分暂归为这类
    /**
     * 代码块
     */
    //private final String PLAIN = "plain";
    public static final String BLOCK_CODE_SLICE = "block_code";

    // 无序列表
    public static final String UNORDERED_LIST_SLICE = "unordered_list";
    // 无序列表: '* '打头
    private static final String UNORDERED_LIST_STAR = "unordered_list_star";
    // 无序列表: '- '打头
    private static final String UNORDERED_LIST_DASH = "unordered_list_dash";

    public static final AbnormalMdSliceTypeMap ABNORMAL_MD_SLICETYPEMAP = new AbnormalMdSliceTypeMap();
    static {
        ABNORMAL_MD_SLICETYPEMAP.put(new SliceTypeMap.TypeEntity(BLOCK_CODE_SLICE, "```", "```",false,true));
        ABNORMAL_MD_SLICETYPEMAP.put(new SliceTypeMap.TypeEntity(UNORDERED_LIST_STAR, "* ", null,true,true));
        ABNORMAL_MD_SLICETYPEMAP.put(new SliceTypeMap.TypeEntity(UNORDERED_LIST_DASH, "- ", null,true,true));
    }


    /**
     * 约定了Markdown文件头声明配置参数键的范围
     */
    // = new String[]{"title", "author", "date", "category", "tags", "comments", "images"};
    public static final Set<String> MD_HEAD_STATE_CONFIG_KEYS = new HashSet<String>(){
        {
            add("title");
            add("author");
            add("date");
            add("category");
            add("tags");
            add("comments");
            add("images");
        }
    };


    /**
     * 系统换行符
     */
    public static final String NEW_LINE = System.getProperty("line.separator");



}
