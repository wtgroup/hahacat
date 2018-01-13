package com.wtgroup.entity;



import java.io.Serializable;

/**
 * 文本片段.
 * 如代码片, 段落片段, ...
 */
public abstract class TextSlice {

    private Serializable sliceType;
    private Serializable content;


    public abstract Serializable getSliceType();

    public abstract void setSliceType(Serializable sliceType);

    public abstract void setContent(Serializable content);

    public abstract Serializable getContent();
}
