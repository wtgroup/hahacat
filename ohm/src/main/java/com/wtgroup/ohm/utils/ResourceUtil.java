package com.wtgroup.ohm.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-05-26-16:53
 */
public class ResourceUtil {

    private static Properties properties = new Properties();

    static {        //静态属性 -> 静态代码块 -> 代码块 -> 构造方法!
        InputStream ohmConf;

        //加载默认配置文件: ohm.properties
        ohmConf = ResourceUtil.class.getClassLoader().getResourceAsStream("ohm.properties");

    }


    /**
     * 默认获取String类型的value
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return String.valueOf(properties.get(key));
    }

    /**
     * Note: 只有根据key拿到"true", 解析结果才会是true. 其他情况都是false
     *
     * @param key
     * @return
     */
    public static Boolean getBoolean(String key) {
        return Boolean.valueOf(get(key));
    }

    public static Integer getInteger(String key) {
        return Integer.valueOf(get(key));
    }

    public static Long getLong(String key) {
        return Long.valueOf(get(key));
    }

    public static Float getFloat(String key) {
        return Float.valueOf(get(key));
    }

    public static Double getDouble(String key) {
        return Double.valueOf(get(key));
    }

}
