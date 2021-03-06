package com.wtgroup.demo.utils.x01_变量名工具类;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 变量名相关工具类, 如命名风格转换, 尤其是 pojo<->数据库
 *
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/5/22 20:49
 */
public class FieldNameUtil {


    /**
     * 根据getter和setter方法命名取得字段名
     * Note: 只会获取public的getter和setter, 请遵循约定大于配置原则
     *
     * @param pojo
     * @return
     */
    public static Set<String> getFieldNames(Class pojo) {
        Method[] methods = pojo.getMethods();
        Set<String> fields = new LinkedHashSet<>();

        for (Method method : methods) {
            System.out.println("方法名:" + method.getName());
            //getter || setter
            if (method.getName().startsWith("get") || method.getName().startsWith("set")) {
                String filedName = convertGetterSetterToFieldName(method.getName());
                fields.add(filedName);
            }
        }


        //测试发现会有 "class"
        fields.remove("class");


        return fields;
    }


    /**getter/setter方法名 -> 对应的字段名
     * @param methodName
     * @return
     */
    private static String convertGetterSetterToFieldName(String methodName) {
        if (!methodName.matches("^[gs]et.*")) {
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


    /**
     * 驼峰命名风格->下划线命名风格
     *
     * @param camelStyle
     * @return
     */
    public String underlineStyle(String camelStyle) {
        char[] chars = camelStyle.toCharArray();
        StringBuilder underlineName = new StringBuilder();
        for (char aChar : chars) {
            if (aChar <= 90 && aChar >= 65) {
                //转小写, 且前面拼上 "_"
                underlineName.append("_").append(aChar += 32);
            } else {
                underlineName.append(aChar);
            }
        }

        return underlineName.toString();
    }


    /**
     * 下划线风格->驼峰风格
     *
     * @param underlineStyle
     * @return
     */
    public String camelStyle(String underlineStyle) {
        String[] split = underlineStyle.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (i != 0) {
                //第二个元素开始, 首字母大写
                sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
            } else {
                sb.append(s);
            }
        }


        return sb.toString();
    }

}
