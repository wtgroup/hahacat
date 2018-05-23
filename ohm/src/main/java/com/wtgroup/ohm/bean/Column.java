package com.wtgroup.ohm.bean;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/5/23 10:46
 */
public class Column {


    private String name;
    private String family;
    private String table;

    public Column() {
    }

    public Column(String name, String family, String table) {
        this.name = name;
        this.family = family;
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public Column setName(String name) {
        this.name = name;
        return this;
    }

    public String getFamily() {
        return family;
    }

    public Column setFamily(String family) {
        this.family = family;
        return this;
    }

    public String getTable() {
        return table;
    }

    public Column setTable(String table) {
        this.table = table;
        return this;
    }


    //列名作为唯一标识
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        return name.equals(column.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
