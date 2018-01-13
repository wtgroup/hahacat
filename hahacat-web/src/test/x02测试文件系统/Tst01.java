package x02测试文件系统;

import org.junit.Test;

import java.io.File;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-26-19:42
 */
public class Tst01 {

    @Test
    public void fun01() {

        File file = new File("C:\\Users\\Nisus\\Desktop\\账号.txt");
        System.out.println(file.getName());
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());



    }


}
