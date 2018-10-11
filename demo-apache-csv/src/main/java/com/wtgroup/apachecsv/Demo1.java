package com.wtgroup.apachecsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/8/8 21:20
 */
public class Demo1 {
    /*
    * 第一行第一列的引号, 不会触发到引号规则.
    *
    * cell开头出现引号, 那么该cell必须用引号包裹.
    *
    *
    * */

    @Test
    public void fun1() throws IOException {
        Reader in = new FileReader("read.csv");
       /* CSVParser parse = CSVFormat.EXCEL.parse(in);
        Iterator<CSVRecord> iterator = parse.iterator();*/
        Iterable<CSVRecord> records = CSVFormat.EXCEL
                .withHeader("name", "age", "sex")
                .parse(in);
        for (CSVRecord record : records) {
            //String lastName = record.get("Last Name");
            //String firstName = record.get("First Name");
            System.out.print(record.get(0) + "\t");
            System.out.print(record.get(1) +"\t");
            System.out.println(record.get("sex"));
            System.out.println();
        }
    }

    @Test
    public void fun11() throws IOException {
        Reader in = new FileReader("read.csv");

        //默认"包裹字符串, 手动设置为'包裹
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withQuote(Character.valueOf('\"')).parse(in);
        for (CSVRecord record : records) {
            //String lastName = record.get("Last Name");
            //String firstName = record.get("First Name");
            System.out.print(record.get(0) + "\t");
            System.out.print(record.get(1));
            System.out.println();
        }
    }


    @Test
    public void fun2() throws IOException {
        FileWriter out = new FileWriter("write.csv");
        //final CSVPrinter printer = CSVFormat.DEFAULT.withHeader("H1", "H2").print(out);
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("h1", "h2");
        csvFormat.printRecord(out,"baby","1");

        out.flush();
        out.close();
    }

    @Test
    public void fun(){

    }


}
