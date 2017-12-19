package com.wtgroup.paser.utils;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.wtgroup.paser.entity.TextSlice;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * 借助3方包解析并渲染
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-19-12:09
 */
public class ParseAndRender {


    private static MarkdownSlicePreprocessor mdsp = new MarkdownSlicePreprocessor4Hexo();
    private BeforeMdParse beforeMdParse = new BeforeMdParse();

    public static String fromList(List<?> list) throws IOException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String content = (String) new JoinAndPreprocess(mdsp, "\n").join(list);


        // markdown to html
        MutableDataSet options = new MutableDataSet();
        // 声明解析的文件类型
        options.setFrom(ParserEmulationProfile.MARKDOWN);
        // table
        options.set(Parser.EXTENSIONS, Arrays.asList(new Extension[]{TablesExtension.create()}));
        // 调用生成器, 基于上述参数创建解析器
        Parser parser = Parser.builder(options).build();
        // 创建渲染器
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(content);

        String html = renderer.render(document);

        return html;
    }

    public String sourceToHTML(String path) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
        beforeMdParse.splitMd(path);
        List<TextSlice> slices = beforeMdParse.getSlices();
        String html = fromList(slices);

        return html;
    }


}
