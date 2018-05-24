package com.wtgroup.ohm.annotation.support;

import com.wtgroup.ohm.annotation.Column;
import com.wtgroup.ohm.bean.Column;
import com.wtgroup.ohm.annotation.HEntity;
import com.wtgroup.ohm.annotation.RowKey;
import net.sf.cglib.beans.BeanGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-05-23-1:43
 */
public class HEntityDescriptor<T> {
    private final Logger log = LoggerFactory.getLogger(HEntityDescriptor.class);
    private T hEntityProxy;
    private String rowKey;
    private String table;
    private String defaultFamily;
    private Set<com.wtgroup.ohm.bean.Column> columns = new LinkedHashSet<>();


    public HEntityDescriptor(Class<T> hEntityClzz) {
        //判断是否还有 @HEntity注解
        if (!hasHEntityAnt(hEntityClzz)) {
            throw new RuntimeException(hEntityClzz.getName() + "缺少@HEntity类注解");
        }


        //生成代理对象
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(hEntityClzz);
        //动态添加需要的属性:
        //LinkedHashSet<Column>
        generator.addProperty("columns", LinkedHashSet.class);
        generator.addProperty("rowKey", String.class);
        generator.addProperty("table", String.class);

        this.hEntityProxy = (T) generator.create();


        //解析注解并丰富属性值
        riching();

    }

    //对外发布最终版的 hEntityProxy
    public T instance() {
        return hEntityProxy;
    }



    private boolean hasHEntityAnt(Class<T> hEntityClzz) {
        return hEntityClzz.isAnnotationPresent(HEntity.class);

    }


    //解析注解, 获取 columns 信息
    private void riching() {

        //解析类注解, 得到表名, 默认列族名
        parseTableAndDefaultFamilyName(hEntityProxy);


        //解析row key
        //解析普通列(属性or方法)
        parseRowKeyAndColumns(hEntityProxy);


        //最终解析完成hbase entity实体类的所有注解, 得到hbase的配置信息, 生成完整的 proxy 对象
        Class<?> hEntityProxyClass = hEntityProxy.getClass();
        //反射赋值
        try {
            Field columns = hEntityProxyClass.getDeclaredField("columns");
            columns.setAccessible(true);
            columns.set(hEntityProxy, columns);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field table = hEntityProxyClass.getDeclaredField("table");
            table.setAccessible(true);
            table.set(hEntityProxy, table);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void parseRowKeyAndColumns(T hEntity) {
        Class hEntityClzz = hEntity.getClass();

        //解析所有字段的注解
        Field[] declaredFields = hEntityClzz.getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            //解析 row key
            parseRowKey(f);

            //解析普通字段
            com.wtgroup.ohm.bean.Column column = parseColumn(f);
            if (column != null) {
            columns.add(column);
            }

        }


        //解析所有方法的注解
        Method[] declaredMethods = hEntityClzz.getDeclaredMethods();
        for (Method m : declaredMethods) {
            m.setAccessible(true);
            //解析 row key
            parseRowKey(m);



            //解析普通字段
            com.wtgroup.ohm.bean.Column column = parseColumn(m);
            if (column != null) {
                columns.add(column);            //方法的优先级高于字段, 字段有重复的会被替换
            }

        }

    }


    private com.wtgroup.ohm.bean.Column parseColumn(AccessibleObject fieldOrMethod) {
        String fieldName = null;
        if (fieldOrMethod instanceof Field) {
            fieldName = ((Field) fieldOrMethod).getName();
        }
        if (fieldOrMethod instanceof Method) {
            //判断是否是getter/setter方法, 仅支持getter/setter方法注解Column
            Method m = (Method) fieldOrMethod;
            if (!m.getName().matches("^[sg]et.*")) {
                log.warn("仅支持getter/setter方法注解Column");
                return null;
            }

            //根据getter/setter得到字段名
            fieldName = convertGetterSetterToFieldName(m.getName());

        }


        //解析普通字段
        Column columnAnt;
        if ((columnAnt = fieldOrMethod.getAnnotation(Column.class)) != null) {
            String colName = columnAnt.name(), family = columnAnt.family();
            if ("".equals(colName) && fieldName != null) {
                //未指定列名时, 以字段名作为列名
                colName = fieldName;

            }
            if ("".equals(family)) {
                //未指定列族名时, 以默认列族名为列族名
                if (defaultFamily == null) {
                    throw new RuntimeException("字段" + fieldName + "未指所属列族, 缺省使用默认列族, 但类注解未指定默认列族");
                }

                family = defaultFamily;
            }

            //创建 Column
            return new com.wtgroup.ohm.bean.Column(colName, family, table);


        }
        return null;
    }


    private String parseRowKey(AccessibleObject fieldOrMethod) {
        Class hEntityClzz = hEntityProxy.getClass();

        if (!fieldOrMethod.isAnnotationPresent(RowKey.class)) {
            log.warn("未发现@RowKey注解");
            return null;
        }

        if (fieldOrMethod instanceof Field ) {
            Field f = (Field) fieldOrMethod;
            //根据getter获取属性值
            try {
                PropertyDescriptor des = new PropertyDescriptor(f.getName(), hEntityClzz);


                return (String) des.getReadMethod().invoke(hEntityProxy);


            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        //若字段没有指定@RowKey, 则寻找方法上的

        //若是getter方法直接调用次方法得到row key
        if (fieldOrMethod instanceof Method) {
            Method m = (Method) fieldOrMethod;
            if (!m.getName().startsWith("get")) {
                log.warn("请在getter上使用@RowKey, 或者指定某个字段为row key, 即在该字段上使用@RowKey注解");
                return null;
            }

            try {
                rowKey = (String) m.invoke(hEntityProxy, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        return rowKey;
    }

    private void parseTableAndDefaultFamilyName(T hEntity) {
        String[] tf = new String[2];
        //构造方法已经拦截了没有注解的情况
        HEntity hEntiyAnt = (HEntity) hEntity.getClass().getAnnotation(HEntity.class);
        this.table = hEntiyAnt.table();
        this.defaultFamily = hEntiyAnt.defaultFamily();

        return;
    }


    /**
     * getter/setter方法名 -> 对应的字段名
     * TODO 规则可能不准
     * @param methodName
     * @return
     */
    private String convertGetterSetterToFieldName(String methodName) {
        if (!methodName.matches("^[gs]et.*")) {
            log.warn("仅支持根据getter/setter猜测字段名. return null");
            return null;
        }


        String f = methodName.substring(3);
        //首字母转为小写即可
        //前提是变量名不是 $ _ 开头
        char[] cs = f.toCharArray();
        if (cs.length == 0) {
            return null;        //切除get/set后没有了字符
        } else if (cs.length == 1) {
            //一个字符时直接转小写
            cs[0] += 32;
        } else {
            //第二个字母是大写字母时, 首字母不转小写
            if ((cs[1] > 90 || cs[1] < 65) && (cs[0] <= 90 && cs[0] >= 65)) {
                cs[0] += 32;        //首字母转小写

            }
        }


        f = String.valueOf(cs);
        return f;
    }


    public String getRowKey() {
        return rowKey;
    }

    public HEntityDescriptor setRowKey(String rowKey) {
        this.rowKey = rowKey;
        return this;
    }

    public String getTable() {
        return table;
    }

    public HEntityDescriptor setTable(String table) {
        this.table = table;
        return this;
    }

    public String getDefaultFamily() {
        return defaultFamily;
    }

    public HEntityDescriptor setDefaultFamily(String defaultFamily) {
        this.defaultFamily = defaultFamily;
        return this;
    }

    public Set<com.wtgroup.ohm.bean.Column> getColumns() {
        return columns;
    }

    public HEntityDescriptor setColumns(Set<com.wtgroup.ohm.bean.Column> columns) {
        this.columns = columns;
        return this;
    }
}

