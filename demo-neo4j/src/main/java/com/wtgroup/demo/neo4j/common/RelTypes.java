package com.wtgroup.demo.neo4j.common;

import org.neo4j.graphdb.RelationshipType;

/**
 * RelationType的枚举类
 * 参照源码注释的建议, 建立自己的枚举类, 维护项目中需要的关系类别们.
 * 实现 RelationshipType 接口, 利用枚举类自带的 name() 方法, 因为创建关系时用到 name() 方法获取类别的名称.
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-17-20:40
 */
public enum RelTypes implements RelationshipType {
    //已有 name() 方法, 故不会提示重写接口的 name()
    KNOWS;

}
