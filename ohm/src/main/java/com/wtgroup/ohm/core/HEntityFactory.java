package com.wtgroup.ohm.core;

import com.wtgroup.ohm.annotation.support.HEntityAntParser;
import com.wtgroup.ohm.bean.HEntityDescriptor;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理hbase实体类
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-05-25-22:44
 */
public class HEntityFactory {

    public static HEntityFactory hEntityFactory=new HEntityFactory();
    private Map<Class, HEntityDescriptor> hEntityDescriptorMap = new HashMap<>();

    private HEntityFactory() {}

    public static HEntityFactory gethEntityFactory() {
        return hEntityFactory;
    }

    public<T> HEntityFactory subscribe(Class<T> hEntityClass) {
        //若已经解析过了, 就不再重复解析了
        if (hEntityDescriptorMap.containsKey(hEntityClass)) {
            return this;
        }

        //解析注解, 得到表名,row key,列族,列
        HEntityAntParser hEntityAntParser = new HEntityAntParser(hEntityClass);
        //根据解析出的信息构建 HEntityDescriptor
        HEntityDescriptor hEntityDescriptor = buildHEntityDescriptor(hEntityAntParser);
        //存入map
        hEntityDescriptorMap.put(hEntityClass, hEntityDescriptor);
        return this;
    }

    private <T> HEntityDescriptor buildHEntityDescriptor(HEntityAntParser<T> parser) {
        HEntityDescriptor hEntityDescriptor = new HEntityDescriptor();
        hEntityDescriptor.setTable(parser.getTable());
        hEntityDescriptor.setDefaultFamily(parser.getDefaultFamily());
        hEntityDescriptor.setRowKey(parser.getRowKey());
        hEntityDescriptor.setColumns(parser.getColumns());
        hEntityDescriptor.sethEntityClass(parser.gethEntityClass());
        //生成实例  TODO 后期可以扩展为传入实例, 这里就不能提前写死实例生成了
        try {
            T hEntity = parser.gethEntityClass().newInstance();
            hEntityDescriptor.sethEntity(hEntity);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return hEntityDescriptor;
    }

    public <T> HEntityDescriptor<T> getHEntityDescriptor(Class<T> hEntityClass) {
        return hEntityDescriptorMap.get(hEntityClass);
    }


}
