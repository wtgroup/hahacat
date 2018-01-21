package com.wtgroup.comb.test;

import org.junit.Test;

/**
 * 强转成string[]数组测试
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-19-10:29
 */
public class StringArrayTest {

    @Test
    public void fun01( ){
        Role role = new Role();
        role.setRoleName("admin");
        Role[] roles = {role};
        boolean isarray = roles.getClass().isArray();

        String s = String.valueOf(roles);
        System.out.println(s);

    }
}
