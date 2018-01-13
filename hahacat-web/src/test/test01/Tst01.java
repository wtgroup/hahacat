package test01;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.wtgroup.paser.utils.MarkdownSlicePreprocessor4Hexo;
import org.apache.commons.lang.StringUtils;
import org.castor.util.StringUtil;
import org.junit.Test;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-18-17:07
 */
public class Tst01 {
//    @Test
//    public void fun01() throws IOException, InstantiationException, IllegalAccessException, InvocationTargetException {
//        // 从文件中读取markdown内容
//        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("testpost01.md");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
//
//        ArrayList<String> list = new ArrayList<String>();
//        String line;
//        while((line = reader.readLine())!=null) {
//            list.add(line);
//        }
//
////        List<String> list = reader.lines().collect(Collectors.toList());
////        String content = Joiner.on("\n").join(list);
//
//        MarkdownSlicePreprocessor4Hexo mdsp = new MarkdownSlicePreprocessor4Hexo();
//        JoinAndPreprocess myJoiner = new JoinAndPreprocess(mdsp, "\n");
//        String content = (String) myJoiner.join(list);
//        System.out.println(content);
//        System.out.println("----------------------------");
//// markdown to image
//        MutableDataSet options = new MutableDataSet();
//        // 声明解析的文件类型
//        options.setFrom(ParserEmulationProfile.MARKDOWN);
//        // table
//        options.set(Parser.EXTENSIONS, Arrays.asList(new Extension[]{TablesExtension.create()}));
//        // 调用生成器, 基于上述参数创建解析器
//        Parser parser = Parser.builder(options).build();
//        // 创建渲染器
//        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
//
//        Node document = parser.parse(content);
//
//        String html = renderer.render(document);
//
//        System.out.println(html);
//
//    }


//    @Test
//    public void fun02() {
//        String s = "Code";
//        String ss = "processCode";
//        System.out.println(ss.endsWith(s));
//    }


    @Test
    public void fun03() {
        String src = "ty, uo u, 80 07j,    j";

        String[] split = StringUtils.split(src, ",");
        for (String s : split) {
            System.out.println(s);
        }
    }

}
