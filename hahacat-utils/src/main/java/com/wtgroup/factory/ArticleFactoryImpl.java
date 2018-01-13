package com.wtgroup.factory;

import com.wtgroup.commons.HhcatPropertiesHandler;
import com.wtgroup.commons.InstanceGetter;
import com.wtgroup.integrate.TemplateIntegrator;
import com.wtgroup.entity.Markdown;
import com.wtgroup.entity.Article;
import com.wtgroup.paser.utils.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

/**
 * 文章生成工厂类
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-22-1:31
 */
public class ArticleFactoryImpl implements ArticleFactory {


    private Charset charset;
    // md文件阅读器
    private MarkdownReaderImpl mdreader;
    // md文件预处理器
    private MarkdownPreprocessor mdPreprocessor;
    // md文件解析和渲染器
    private MarkdownParserRender mdprer;
    // 存取Markdown实例
    private List<Markdown> markdowns;

    // 指定slice的预处理器
    private MarkdownSlicePreprocessor mdsp;
    // 指定解析渲染器
    private MarkdownParserRender mdpr;
    //
    private TemplateIntegrator tplIntegrator;
    // 文章html模板
    private File articleTemplate;

    // 存取解析好的html类型的文章
    private List<Article> articles;


    /**
     * @param properties 站点核心配置参数
     */
    public ArticleFactoryImpl(Properties properties) {
        // 根据配置map, 拿到本工厂所需的对象们
        HhcatPropertiesHandler handler = new HhcatPropertiesHandler();


        String[] sourceLoactions = handler.handleSourceLocations(properties.getProperty("sourceLoactions"));
        this.mdreader = new MarkdownReaderImpl(sourceLoactions);


        this.charset = Charset.forName(properties.getProperty("charset"));

        articleTemplate = handler.handleArticleTemplate(properties.getProperty("articleTemplate"));


        try {
            // 获取slice预处理器
            this.mdsp = new InstanceGetter<MarkdownSlicePreprocessor>().getInstanceForName(properties.getProperty("markdownSlicePreprocessor"));
            // 获取模板整合器
            this.tplIntegrator = new InstanceGetter<TemplateIntegrator>().getInstanceForName(properties.getProperty("templateIntegrator"));
            tplIntegrator.setTemplate(articleTemplate);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建必须的实例失败!");
        }

        this.mdPreprocessor = new MarkdownPreprocessorImpl();
        this.mdpr = new MarkdownParserRenderImpl();

    }




    private List<Article> generateArticles() throws Exception {
        // 读取本地md文件形成list
        markdowns = mdreader.getMarkdownFiles();

        // 预处理
        markdowns = mdPreprocessor.preprocess(this.markdowns, charset, mdsp);


        // 解析和渲染
        this.articles = mdpr.parseAndRender(markdowns);

        // 模板整合
        this.articles = tplIntegrator.integrate(this.articles, articleTemplate);

        return this.articles;
    }

    @Override
    public List<Article> getArticles() {

        if (articles == null) {
            try {
                generateArticles();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return articles;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        generateArticles();
    }
}
