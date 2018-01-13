package com.wtgroup.entity;



import java.util.*;

/**
 * 存储slice类型和对应的开始和结束标签.
 * 这里有些内置的类型. <b>注意normal类型的不能添加进来.</b>
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-19-8:58
 */
public class AbnormalMdSliceTypeMap implements SliceTypeMap<String,String,String> {



    private List<SliceTypeMap.TypeEntity> types = new ArrayList<SliceTypeMap.TypeEntity>();



    public AbnormalMdSliceTypeMap() {
    }


    public int size() {
        return types.size();
    }

    public boolean isEmpty() {

        return size() > 0 ? false : true;
    }


    public boolean containsTypeName(String name) {
        boolean flag = false;
        for (TypeEntity type : types) {
            if (type.getTypeName().equals(name.trim())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public boolean containsStartTag(String stag) {
        boolean flag = false;
        for (TypeEntity type : types) {
            if (type.getStartTag().equals(stag.trim())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public boolean containsEndTag(String etag) {
        boolean flag = false;
        for (TypeEntity type : types) {
            if (type.getEndTag().equals(etag.trim())) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    public TypeEntity getByTypeName(String typename) {
        TypeEntity entity = null;
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getTypeName().equals(typename.trim())) {
                entity = types.get(i);
            }
        }

        return entity;
    }

    public TypeEntity getByStartTag(String tag) {
        TypeEntity entity = null;
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getStartTag().equals(tag.trim())) {
                entity = types.get(i);
            }
        }

        return entity;
    }

    public TypeEntity getByEndTag(String tag) {
        TypeEntity entity = null;
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getEndTag().equals(tag.trim())) {
                entity = types.get(i);
            }
        }

        return entity;
    }


    public TypeEntity put(TypeEntity entity) {
        TypeEntity old = null;
        for (TypeEntity type : types) {
            if (type == entity || type.hashCode() == entity.hashCode() ||
                    type.getTypeName().equals(entity.getTypeName()) ||
                    type.getStartTag().equals(type.getStartTag()) ||
                    type.getEndTag().equals(type.getEndTag())) {
                // 碰到已存在的, 替换掉旧的
                old = type;
                types.remove(type);
                break;
            }
        }
        types.add(entity);
        return old;
    }

    public void remove(TypeEntity entity) {
        types.remove(entity);
    }


    public void clear() {
        for (TypeEntity type : types) {
            type = null;
        }
    }


    public List<TypeEntity> entryList() {
        return types;
    }


}
