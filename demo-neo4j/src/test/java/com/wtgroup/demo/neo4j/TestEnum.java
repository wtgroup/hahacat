package com.wtgroup.demo.neo4j;

import org.junit.Test;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-17-20:29
 */
public class TestEnum {

    /*
    * 枚举类默认有 name() 方法, 返回的就是枚举类标记*/

    public enum Person {
        man("male"),woman("female");
        private String alias;
        Person(String alias) {
            this.alias = alias;
        }
    }

    @Test
    public void fun(){
        System.out.println(Person.man.alias);
        System.out.println(Person.man.name());
    }

}
