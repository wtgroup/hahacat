package com.wtgroup.ohm;

import com.wtgroup.ohm.utils.HbaseBeanUtils;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/5/27 22:35
 */
public class TestHbaseBeanUtils
{
    @Test
    public void fun1() throws IllegalAccessException, NoSuchFieldException {
        Person p = new Person();

        // -- boolean --
        Field f = Person.class.getDeclaredField("sex");

        HbaseBeanUtils.setProperty(p,f, Bytes.toBytes(true));

        System.out.println(p);

        // -- boolean --
        Field f2 = Person.class.getDeclaredField("sex2");

        HbaseBeanUtils.setProperty(p,f2, Bytes.toBytes(true));

        System.out.println(p);



    }
}
