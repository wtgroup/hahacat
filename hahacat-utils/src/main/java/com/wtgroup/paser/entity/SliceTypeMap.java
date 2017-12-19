package com.wtgroup.paser.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于存放文本片段类型映射: 类型名称=起始标签=结束标签.
 * 这里面含有的类型将被预处理器(SlicePreprocessor或其子类)处理.
 */
public interface SliceTypeMap<N extends Serializable,ST extends Serializable,ET extends Serializable> {


    List<SliceTypeMap.TypeEntity> types = new ArrayList<SliceTypeMap.TypeEntity>();


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


    class TypeEntity<N extends Serializable,ST extends Serializable,ET extends Serializable> {
        private N typeName;
        private ST startTag;
        private ET endTag;

        public TypeEntity(N typeName, ST startTag, ET endTag) {
            if (null == typeName || "".equals(typeName)) {
                throw new RuntimeException("'typename' can't be '' or null");
            }
            if (null == startTag || "".equals(startTag)) {
                throw new RuntimeException("'startTag' can't be '' or null");
            }
            if (null == endTag || "".equals(endTag)) {
                throw new RuntimeException("'endTag' can't be '' or null");
            }
            this.typeName = typeName;
            this.startTag = startTag;
            this.endTag = endTag;
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
    }

}
