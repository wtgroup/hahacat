package com.wtgroup.paser.utils;

import com.google.common.base.Joiner;
import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.wtgroup.constant.Profile;
import com.wtgroup.entity.Markdown;
import com.wtgroup.entity.MarkdownSlice;
import com.wtgroup.entity.Article;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-22-15:20
 */
public class MarkdownParserRenderImpl implements MarkdownParserRender {
    private Joiner joiner = Joiner.on(Profile.NEW_LINE);
    private List<Article> articles = new ArrayList<Article>();


    @Override
    public List<Article> parseAndRender(List<Markdown> markdowns) {

        for (Markdown markdown : markdowns) {
            Article article = parseAndRender(markdown);
            articles.add(article);
        }

        return articles;
    }

    public Article parseAndRender(Markdown md) {
        List<MarkdownSlice> slices = md.getSlices();
        String mdContent = joiner.join(slices);

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

        Node document = parser.parse(mdContent);

        String html = renderer.render(document);

        Article article = new Article(md);
        article.setContent(html);
        return article;
    }



}
