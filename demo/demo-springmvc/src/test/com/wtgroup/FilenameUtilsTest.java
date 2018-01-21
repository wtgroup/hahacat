package com.wtgroup;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-18-12:45
 */
public class FilenameUtilsTest {

    @Test
    public void fun01() {
        String fn = "D:\\DevelopKit\\upload\\Image 4.png";

        System.out.println(FilenameUtils.getBaseName(fn));  //=>Image 4  即获取文件名

        System.out.println(FilenameUtils.getPath(fn));  //=>DevelopKit\\upload\\

        System.out.println(FilenameUtils.getFullPath(fn));  //=>D:\\DevelopKit\\upload\\

        System.out.println(FilenameUtils.getPrefixLength(fn));  //=>3 上级目录层级数目

        System.out.println(FilenameUtils.getExtension(fn)); //=>png

    }


}
