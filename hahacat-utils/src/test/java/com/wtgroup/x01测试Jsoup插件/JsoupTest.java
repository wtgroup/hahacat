package com.wtgroup.x01测试Jsoup插件;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-21-22:19
 */
public class JsoupTest {

    public static void main(String[] args) throws IOException {
        File input = new File("D:\\Work\\JAVA\\workspace\\hahacat\\hahacat-web\\src\\main\\webapp\\test01.html");
        FileOutputStream fos = new FileOutputStream("D:\\Work\\JAVA\\workspace\\hahacat\\hahacat-web\\src\\main\\webapp\\test01_jsoup.html");
        OutputStreamWriter os = new OutputStreamWriter(fos, "utf-8");
        Document doc = Jsoup.parse(input, "UTF-8");

//        Elements select = doc.select("div[class='post-body']");
//        Elements select = doc.select("div.post-body");
        Element e = doc.getElementById("post-body");


        String id = e.id();
        String s = e.className();
        Element child = e.child(0);
        Element text = child.text("修改的标题1");
        String html = doc.html();
        os.write(html);
        os.close();

//        Elements links = doc.getElementsByTag("a");
//        for (Element link : links) {
//            String linkHref = link.attr("href");
//            System.out.println(linkHref);
//            String linkText = link.text();
//            System.out.println(linkText);
//        }
    }
}
