import org.junit.Test;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.FileHandler;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/5/22 20:00
 */
public class TestFiledUtil {
    private String _hou;

    private Set<String> fields = new LinkedHashSet<String>();


    @Test
    public void fun01() {
        Set<String> filedNames = getFiledNames(Pojo.class);
        System.out.println(filedNames);

        for (String filedName : filedNames) {
            System.out.println("camel>>" + camelStyle(filedName));
            System.out.println("underline>>" + underlineStyle(filedName));
        }
    }


    public Set<String> getFiledNames(Class pojo) {
        Method[] methods = pojo.getMethods();
        for (Method method : methods) {
            System.out.println("方法名:" + method.getName());
            //getter || setter
            if (method.getName().startsWith("get") || method.getName().startsWith("set")) {
                String filedName = methodName2filedName(method.getName());
                fields.add(filedName);
            }
        }


        //测试发现会有 "class"
        fields.remove("class");


        return fields;
    }


    private String methodName2filedName(String methodName) {
        String f = methodName.substring(3);
        //首字母转为小写即可
        //前提是变量名不是 $ _ 开头
        char[] cs = f.toCharArray();
        if (cs[0] <= 90 && cs[0] >= 65) {
            cs[0] += 32;        //首字母转小写

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
