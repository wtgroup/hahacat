package com.wtgroup.demo.test.utils.hithchartdatainterface;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 省市县类型的实体.
 * 辅助封装画图数据.
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-11-17:34
 */
public class PCDEntity {
    private Serializable id;         //区域的ID, 必须具有唯一性
    private String name;       //区域的名称
    private double value=0.0;      //区域的数值

    private Map<Serializable,PCDEntity> children = new HashMap<Serializable,PCDEntity>(); //下级数据集合


    public Map<Serializable, PCDEntity> getChildren() {
        return children;
    }

    public void setChildren(Map<Serializable, PCDEntity> children) {
        this.children = children;
    }

    public Serializable getId() {
        return id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }


    public void setValue(double value) {
        this.value = value;
    }
}
