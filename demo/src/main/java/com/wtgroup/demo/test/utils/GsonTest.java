package com.wtgroup.demo.test.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.wtgroup.demo.utils.SimpleExclusionStrategy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-10-15:33
 */
public class GsonTest {

    private Gson gson = new Gson();

    @Test
    public void fun01() {
        //初始化数据
//        SimpleData data_jane = new SimpleData("Jane" , new Integer[]{1 , 0 , 4});
//        SimpleData data_john = new SimpleData("Jone" , new Integer[]{5 , 7 , 3});
//使用Google的Json工具
        Integer i = gson.fromJson("100", int.class);
        System.out.println(i);

        Double d = gson.fromJson("99.234", double.class);
        System.out.println(d);

    }

    /*对象->json, 只会根据属性来, 单纯增加getter却没有对应的属性, 不会出现在结果中.*/
    // 对象操作
    @Test
    public void fun02() {
        User u = new User("小明", 78, true);
        String s = gson.toJson(u);
        System.out.println(s);

        System.out.println("--------------");
        String ss = "{\"myNickName\":\"小明\",\"myAge\":78,\"sex\":true}";
        User user = gson.fromJson(ss, User.class);
        System.out.println(user);

    }

    /**
     * 基于注解,json->Object.
     */
    /*使用Gson的注解,
    * json->Object, 只要注解配置了的字段名称, 均可被正确识别, 多个同时出现时, 以最后一个为准*/
    @Test
    public void fun03() {
        System.out.println("--------------");
        String ss = "{\"my_nick_name\":\"小明\",\"myAge\":78,\"sex\":true}";
        User user = gson.fromJson(ss, User.class);
        System.out.println(user);

    }

    /**
     * 基于注解,Object->json.
     */
    /*Object->json, 字段名称会变成注解配置的value.
    * 这个针对准备前端框架的特定数据格式特别有用*/
    public void fun04() {
        User u = new User("小明", 78, true);
        String s = gson.toJson(u);
        System.out.println(s);
    }


    // ==集合类型转换== //

    /**
     * 数组
     */
    @Test
    public void fun05() {
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
        String[] strings = gson.fromJson(jsonArray, String[].class);
        for (String s : strings) {
            System.out.println(s);
        }

        // map
        HashMap<String, String> map = new HashMap<>();
        map.put("address", "洪福");
        map.put("歌手", "伍佰");
        System.out.println(gson.toJson(map, map.getClass()));

    }

    /**
     * list(常用)
     * 1. 不用泛型
     * 2. 使用泛型, 由于泛型擦除机制, 复杂点.
     */
    @Test
    public void fun06() {
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";

        // 不使用泛型
        List list = gson.fromJson(jsonArray, List.class);
        System.out.println(list);

        // List->json
        User u1 = new User("小光");
        User u2 = new User("华仔");
        User u3 = new User("吴京");
        ArrayList<User> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        System.out.println(gson.toJson(users, List.class));

        // json->List 使用泛型
        String slist = "[{\"my_nick_name\":\"小光\",\"sex\":false},{\"my_nick_name\":\"华仔\",\"sex\":false},{\"my_nick_name\":\"吴京\",\"sex\":false}]";
        List<User> ulist = gson.fromJson(slist, new TypeToken<List<User>>() {
        }.getType());
        System.out.println(ulist);
    }

    /**
     * 字段过滤规则: 基于策略
     */
    public void fun07() {
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        // 这里作判断，决定要不要排除该字段,return true为排除
                        if ("finalField".equals(f.getName())) return true; //按字段名排除
                        Expose expose = f.getAnnotation(Expose.class);
                        if (expose != null && expose.deserialize() == false) return true; //按注解排除
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 直接排除某个类 ，return true为排除
                        return (clazz == int.class || clazz == Integer.class);
                    }
                })
                .create();

//        作者：怪盗kidou
//        链接：https://www.jianshu.com/p/0e40a52c0063
//        來源：简书
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

    }


    @Test
    public void fun08() {

        // 使用自定义扩展的类
        SimpleExclusionStrategy exs = new SimpleExclusionStrategy();
        // Integer 不等于 int
        exs.addExcludesByClass(Integer.class, int.class);
        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(exs).create();
        User u = new User("黄忠", 88, false);
        System.out.println(gson.toJson(u));

        exs.addExcludesByName("sex");
        Gson gson1 = new GsonBuilder().addSerializationExclusionStrategy(exs).create();
        System.out.println(gson1.toJson(u));

        SimpleExclusionStrategy exs2 = new SimpleExclusionStrategy();
        exs2.addIncludesByName("myAge");
        Gson gson2 = new GsonBuilder().addSerializationExclusionStrategy(exs2).create();
        System.out.println(gson2.toJson(u));


    }


    @Test
    public void fun09() {
        // 嵌套
        User u = new User("黄忠", 88, false);

        System.out.println("----------------");
        User u2 = new User("小黄总", 33, true);
        u.setSubUser(u2);
        SimpleExclusionStrategy exs3 = new SimpleExclusionStrategy().addExcludesByClass(String.class);
        Gson gson3 = new GsonBuilder().addSerializationExclusionStrategy(exs3).create();
        System.out.println(gson3.toJson(u));
    }








    class User {
        // 前台习惯使用"_"命名方式, 而我是java程序员, 习惯驼峰命名规则.
        // 或者, 前台框架的字段名称规范限制.
//        @SerializedName("一个值时直接写")
//        @SerializedName(value = "my_nick_name", alternate = {"myNickName", "nickName"})
        private String myNickName;
        private Integer myAge;
        private boolean sex;

        public void setSubUser(User subUser) {
            this.subUser = subUser;
        }

        private User subUser;

        public User(String name) {
            this.myNickName = name;
        }

        public User(String name, Integer age, boolean sex) {
            this.myNickName = name;
            this.myAge = age;
            this.sex = sex;
        }


        @Override
        public String toString() {
            return "User{" +
                    "myNickName='" + myNickName + '\'' +
                    ", myAge=" + myAge +
                    ", sex=" + sex +
                    '}';
        }
    }

}
