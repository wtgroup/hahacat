package javase.x01;

import org.junit.Test;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-22-19:51
 */
public class Demo {

    /**
     * 牛客网题  string
     */
    @Test
    public void fun01(){
        String msg = "taobao";
        String a = "tao" + "bao";
        String b = "tao";
        String c = "bao";

        System.out.println(a == msg);   //=>true
        System.out.println((b + c) == msg); //=>false
    }

    /**
     * 测试
     */
    @Test
    public void fun02(){
        String msg = "taobao";  //这个存放于常量池中
        String msg1 = "taobao";  //这个存放于常量池中

        //那么在new一个同样内容的呢?
        String a = new String("taobao");
        String b = new String("taobao");

        System.out.println(a == msg);   //=>false
        System.out.println(a == b);     //=>false
        System.out.println(msg == msg1);    //=>true

        /*结论: 也就是说, 直接字符串赋值会在常量池中,
        * 而new的则在堆中开辟空间存放"taobao"*/
    }

}
