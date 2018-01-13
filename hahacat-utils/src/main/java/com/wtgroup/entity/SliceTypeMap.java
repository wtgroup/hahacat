package com.wtgroup.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.util.List;

/**
 * 用于存放文本片段类型映射: 类型名称=起始标签=结束标签.
 * 这里面含有的类型将被预处理器(SlicePreprocessor或其子类)处理.
 */
public interface SliceTypeMap<N extends Serializable,ST extends Serializable,ET extends Serializable> {


    //static List<SliceTypeMap.TypeEntity> TYPES = new ArrayList<SliceTypeMap.TypeEntity>();


    public int size();

    public boolean isEmpty();

    public boolean containsTypeName(N name);

    public boolean containsStartTag(ST stag);

    public boolean containsEndTag(ET etag);


    public SliceTypeMap.TypeEntity getByTypeName(N typename);

    public SliceTypeMap.TypeEntity getByStartTag(ST tag);

    public SliceTypeMap.TypeEntity getByEndTag(ET tag);


    public TypeEntity put(TypeEntity entity);

    public void remove(SliceTypeMap.TypeEntity entity);


    public void clear();


    public List<SliceTypeMap.TypeEntity> entryList();


    /**
     * 目前只支持块状slice, 且开始结束标签一致(对称).
     * @param <N>
     * @param <ST>
     * @param <ET>
     */
    class TypeEntity<N extends Serializable,ST extends Serializable,ET extends Serializable> {
        private N typeName;
        private ST startTag;
        private ET endTag;
        // 是否仅有开始标记
        private boolean OnlyStartTag;
        // 是否块状(block), 对应行内(inline)
        private boolean isBlock;

        public TypeEntity(N typeName, ST startTag, ET endTag, boolean OnlyStartTag, boolean isBlock) {
            if (null == typeName || "".equals(typeName)) {
                throw new RuntimeException("'typename' can't be '' or null");
            }
            if (null == startTag || "".equals(startTag)) {
                throw new RuntimeException("'startTag' can't be '' or null");
            }
            if (!OnlyStartTag &&  (null == endTag || "".equals(endTag))) {
                throw new RuntimeException("'startTag' can't be '' or null, because 'OnlyStartTag' is false.");
            }
            this.typeName = typeName;
            this.startTag = startTag;
            this.endTag = endTag;
            this.OnlyStartTag = OnlyStartTag;
            this.isBlock = isBlock;
        }


        public N getTypeName() {
            return typeName;
        }

        public void setTypeName(N typeName) {
            this.typeName = typeName;
        }

        public ST getStartTag() {
            return startTag;
        }

        public void setStartTag(ST startTag) {
            this.startTag = startTag;
        }

        public ET getEndTag() {
            return endTag;
        }

        public void setEndTag(ET endTag) {
            this.endTag = endTag;
        }

        public boolean isOnlyStartTag() {
            return OnlyStartTag;
        }

        public void setOnlyStartTag(boolean onlyStartTag) {
            OnlyStartTag = onlyStartTag;
        }

        public boolean isBlock() {
            return isBlock;
        }

        public void setBlock(boolean block) {
            isBlock = block;
        }
    }

}
