package com.wtgroup.utils.pcddata.integrate;

import com.wtgroup.utils.pcddata.entity.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 将Markdown文章整合进给定的文章模板中.
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-23-13:48
 */
public class TemplateIntegrator4Hexo implements TemplateIntegrator {

    private File template;
    private Charset charset = Charset.forName("UTF-8");
    private List<Article> articles;

    public TemplateIntegrator4Hexo() {
    }


    public void setTemplate(File htmlTemplate) {
        this.template = htmlTemplate;
    }

    @Override
    public void setCharset(Charset charset) {
        this.charset = charset;
    }


    // 加载模板, 将解析好的md内容嵌入模板
    public Article integrate(Article article, File template) throws IOException {
        // 获取文章的配置参数map
        Map<String, String[]> cfgs = article.getSourceDoc().getArticleConfigs();
        String content = (String) article.getContent();
        // 解析模板html文件, 得到Document对象
        Document doc = Jsoup.parse(template, charset.toString());

        // head
            // meta
        head$metaIntegrate(cfgs,doc);

        // article
            // header
        article$headerIntegrate(cfgs, doc);


        // body
        bodyIntegrate(content,doc);

        // 返回
        article.setContent(doc.html());
        // 将整合好的document对象存储起来, 后面要用
        article.setDocument(doc);
        return article;
    }

    @Override
    public List<Article> integrate(List<Article> articles, File template) throws IOException {
        List<Article> retArticles = new ArrayList<Article>();
        for (Article article : articles) {
            Article integrate = integrate(article, template);
            retArticles.add(integrate);
        }

        return retArticles;
    }


    /**
     * article>>header
     * @param cfgs
     * @param doc
     */
    private void article$headerIntegrate(Map<String, String[]> cfgs, Document doc) {
        // <h1 class="post-title" itemprop="name headline">标题</h1>
        doc.select("h1.post-title").get(0).html(cfgs.get("title")[0]);

        // header>>..>>time
        // <time title="创建于" itemprop="dateCreated datePublished" datetime="2017-12-15T20:43:23+08:00"> 2017-12-15 </time>
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss zzz");
        String date = cfgs.get("date")[0];
        Date datetime = null;
        try {
            datetime = sdf.parse(date);
        } catch (ParseException e) {
            // 日期字符串解析失败, 则使用当前时间
            datetime = new Date();
        }
        Element timeEl = doc.select("article header time").get(0);
        timeEl.attr("datetime", sdf.format(datetime));
        timeEl.text(date);

    }

    private void bodyIntegrate(String content,Document doc) {
        doc.select("div.post-body").html(content);

    }

    /**
     * head>>meta
     * @param articleConfigs
     * @param doc
     */
    private void head$metaIntegrate(Map<String, String[]> articleConfigs, Document doc) {
        //<meta property="og:title" content="">
        String title = articleConfigs.get("title")[0];
        doc.select("meta[property='og:title']").attr("content", title);

        //<meta property="og:updated_time" content="">
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        long time = System.currentTimeMillis();
        String fmtTime = sdf.format(time);
        doc.select("meta[property=\"og:updated_time\"]").attr("content", fmtTime);

        //<meta name="twitter:title" content="">
        doc.select("meta[name=\"twitter:title\"]").attr("content", title);

    }


}
