package com.wtgroup.x02测试字符转标准时间在转字符;

import org.junit.Test;
import sun.misc.Regexp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-25-15:01
 */
public class Tst01 {

    @Test
    public void fun01() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


        String dateString = "2107.02.12";


        if (dateString.contains("-") || dateString.contains(".")) {
            dateString = dateString.replace("-", "/");
            dateString = dateString.replace(".", "/");
        }
        Date parse = sdf.parse(dateString);
        String fmtDateString = sdf.format(parse);
        System.out.println(fmtDateString);

//
//        Date date = sdf.parse(s);
//        System.out.println(date);
//        String format1 = sdf.format(date);
//        System.out.println(format1);

    }



}
