package com.wtgroup.demo.springcache;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 *
 *
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/6/7 14:32
 */
public class SpringCacheTest {

/*
* https://www.ibm.com/developerworks/cn/opensource/os-cn-spring-cache/
* 当账号数据发生变更，那么必须要清空某个缓存，另外还需要定期的清空所有缓存，以保证缓存数据的可靠性。*/

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "springmvc.xml");// 加载 spring 配置文件

        AccountService s = (AccountService) context.getBean("accountService");
        AccountService2 s2 = (AccountService2) context.getBean("accountService2");

        System.out.println("me - 1");
        System.out.println(s.getAccountByName("somebody"));
        System.out.println("me - 2");
        System.out.println(s.getAccountByName("somebody"));
        System.out.println("other - 1");
        Account otherbody = s2.getAccountByName("otherbody");
        //可以清空别的缓存名下的缓存吗
        s2.evictCache(otherbody);

        System.out.println("me - 3");
        System.out.println(s.getAccountByName("somebody"));

//        // 第一次查询，应该走数据库
//        System.out.println("first query...");
//        s.getAccountByName("somebody");
//        // 第二次查询，应该不查数据库，直接返回缓存的值
//        System.out.println("second query...");
//        Account somebody = s.getAccountByName("somebody");
//        System.out.println(somebody);
//
//        //清空缓存后呢
//        s.evictCache(somebody.setUserName("libai"));
//
//        // 第三次查询, 预期: 查数据库\
//        System.out.println("第三次查询");
//        System.out.println(s.getAccountByName("somebody"));
//
//        System.out.println("第4次查询");
//        System.out.println(s.getAccountByName("somebody"));
//
//
//        System.out.println();
    }


}



