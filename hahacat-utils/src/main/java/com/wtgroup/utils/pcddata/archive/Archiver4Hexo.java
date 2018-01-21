package com.wtgroup.utils.pcddata.archive;

import com.wtgroup.utils.pcddata.entity.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-25-12:41
 */
public class Archiver4Hexo implements Archiver {
    private String webapproot;

    // 站点首页
    private File homePage;


    // 编码
    private Charset charset;

    public Archiver4Hexo() {
        webapproot = System.getProperty("webapp.root");
        homePage = new File(webapproot + "index.html");

    }


    public void archive(List<Article> articles, String publish) {
        Document doc = null;
        try {
            doc = Jsoup.parse(homePage, charset.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("解析首页html文件失败!");
        }


        // 按日期排序(升序)
        Collections.sort(articles);
        // 遍历
        for (int i = articles.size() - 1; i >= 0; i--) {
            // 存盘
            doSave(articles.get(i), publish);
            // 更新首页
            updateHomePage(articles.get(i), doc, publish);

            // TODO 暂只解决首页更新问题
            // 更新archives


            // 更新categories


            // 更新tags
        }


        // 将更新好的首页Document写入磁盘
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(homePage), charset.name());
            osw.write(doc.toString());
            osw.flush();
            osw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 在原有的article节点之前插入新的article节点
     *
     * @param article
     * @param homePageHtml
     * @param publish 文章存储的根文件夹. 用来拼接文章的引用路径(相对)
     */
    private void updateHomePage(Article article, Document homePageHtml, String publish) {

        //<section id="posts" class="posts-expand">前面追加article
        Document articleDoc = article.getDocument();
        if (articleDoc == null) {
            throw new RuntimeException("article的document属性为null!, 确认文章整合模板后是否将document对象设置进来.");
        }

        // 获取文章的<article class="post post-type-normal" itemscope itemtype="http://schema.org/Article">节点
        Element articleEle = articleDoc.select("article.post").get(0);

        // TODO: 2018/1/4 class=post-title添加链接 totest
        // 文章在首页展示, 标题要用<a>包裹
        // <h1 class="post-title" itemprop="name headline"> <a class="post-title-link" href="/publish/2017/12/15/mypost/" itemprop="url">hexo的第一篇文章</a></h1>
        String href =  "/" + publish + "/" +article.getArticleRelUrl();
        String a_title = "<a class=\"post-title-link\" href=\"" + href + "\" itemprop=\"url\">" + article.getArticleConfigs().get("title")[0] + "</a>";
        articleEle.select("h1.post-title").get(0).html(a_title);

        // 顶部追加至首页的<section>节点
        try {
            homePageHtml.getElementById("posts").prependChild(articleEle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文章整合进首页失败!");
        }


//        Element articleEleOri = homePageHtml.select("article").get(0);
//        Element articleEle = articleEleOri.clone();
//
//        Elements header = articleEle.select("header");
//        header.select("h1 a").get(0).attr("href", article.getArticleRelUrl());
//        header.select("h1 a").get(0).text(article.getArticleConfigs().get("title")[0]);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String date = article.getArticleConfigs().get("date")[0];
//        header.select("span.post-time time").get(0).attr("datetime", date);
//        header.select("span.post-time time").get(0).text(date);
//        header.select("span.post-category span a").get(0).attr("href", "/categories/" + article.getArticleConfigs().get("category")[0]);
//        header.select("span.post-category span a span").get(0).text(article.getArticleConfigs().get("category")[0]);
//
//        //<div class="post-body"
//        articleEle.select("div.post-body").get(0).html((String) article.getContent());
//
//
//        homePageHtml.getElementById("posts").prependChild(articleEle);

    }


    /**
     * 将文章存盘
     */
    public void doSave(Article article, String publish) {
        String relativeArticleDir = article.getArticleRelUrl();
        String separator = File.separator;
        String savefiledir = webapproot + publish + separator + relativeArticleDir;

        File saveFileDir = new File(savefiledir);
        try {
            if (!saveFileDir.exists()) {
                saveFileDir.mkdirs();
            }
            String savefilename = saveFileDir + separator + "index.html";
            File saveFileName = new File(savefilename);
            if (!saveFileName.exists()) {
                saveFileName.createNewFile();
            }

            FileOutputStream fo = new FileOutputStream(saveFileName);
            fo.write(((String) article.getContent()).getBytes(charset));
            fo.flush();
            fo.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }


}
