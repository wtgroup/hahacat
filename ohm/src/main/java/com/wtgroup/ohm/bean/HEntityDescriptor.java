package com.wtgroup.ohm.bean;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-05-25-22:58
 */
public class HEntityDescriptor<T> implements Serializable{
    private Class<T> hEntityClass;
    private T hEntity;
    private RowKey rowKey;
    private String table;
    private String defaultFamily;
    private Set<Column> columns = new LinkedHashSet<>();

    public HEntityDescriptor() {
    }



    public RowKey getRowKey() {
        return rowKey;
    }

    public void setRowKey(RowKey rowKey) {
        this.rowKey = rowKey;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDefaultFamily() {
        return defaultFamily;
    }

    public void setDefaultFamily(String defaultFamily) {
        this.defaultFamily = defaultFamily;
    }

    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }

    public Class<T> gethEntityClass() {
        return hEntityClass;
    }

    public void sethEntityClass(Class<T> hEntityClass) {
        this.hEntityClass = hEntityClass;
    }

    public T gethEntity() {
        return hEntity;
    }

    public void sethEntity(T hEntity) {
        this.hEntity = hEntity;
    }
}
