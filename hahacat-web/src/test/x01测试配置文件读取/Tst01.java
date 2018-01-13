package x01测试配置文件读取;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-23-11:47
 */
public class Tst01 {


    @Test
    public void fun01() throws IOException {
        Properties prop = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream src = classLoader.getResourceAsStream("hhcat.properties");
        prop.load(src);

        System.out.println(prop.getProperty("markdownLocation"));
        System.out.println(prop.getProperty("publish"));
        System.out.println(prop.getProperty("archivedBy"));

        System.out.println(classLoader.getResource(""));
        System.out.println(classLoader.getResource("/"));

        //
        String root = classLoader.getResource("").getPath();

        File mddir = new File(root+prop.getProperty("markdownLocation"));
        if (mddir.isDirectory()) {
            File[] files = mddir.listFiles();
            for (File file : files) {
                System.out.println(file.getName());
            }
        }


    }




}
