package com.wtgroup.entity;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * HTML实体类
 * <p>
 * md
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-22-11:14
 */
public class Article extends File implements Text {
    // 存储网页全文, 一般为字符串
    private Serializable content;
//    // 存储Jsoup的Document对象
    private Document document;
    private Map<String, String[]> articleConfigs;  // 暂为源文件的配置
    private Text sourceDoc;
    // 按照日期生成的相对路径: /2017/12/28
    private String articleRelUrl;

    private Article(String pathname) {
        super(pathname);
    }

    public Article(Text sourceTxt) {
        this(sourceTxt.getAbsolutePath());
        this.sourceDoc = sourceTxt;
        this.articleConfigs = sourceDoc.getArticleConfigs();
    }

    public Serializable getContent() {
        return content;
    }

    public void setContent(Serializable content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return ""+content;
    }



    public Text getSourceDoc() {
        return sourceDoc;
    }

    @Override
    public Map<String, String[]> getArticleConfigs() {
        return articleConfigs;
    }


    /**
     * 改成可按日期排序
     *
     * @param another
     * @return
     */
    @Override
    public int compareTo(File another) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String self = this.getArticleConfigs().get("date")[0];
        Article anotherArt = (Article) another;
        String anotherDateStr = anotherArt.getArticleConfigs().get("date")[0];

        try {
            Date selfDate = sdf.parse(self);
            Date anotherDate = sdf.parse(anotherDateStr);
            // 比较日期大小
            return selfDate.compareTo(anotherDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public String getArticleRelUrl() {
        if (StringUtils.isNotBlank(this.articleRelUrl)) {
            return this.articleRelUrl;
        }
        return genRelativeSaveDir(this);
    }


    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * 生成链接用的web绝对路径, 最低到不带后缀的文件名
     * 后续建立连接还需要用
     *
     * @param article
     * @return
     */
    private String genRelativeSaveDir(Article article) {

        String fmtDateString = null;
        String dateString = null;
        String[] dates = article.getArticleConfigs().get("date");
        if(dates.length>0) {
            dateString = dates[0];
        }

        // 没有指定date参数, 则使用当前时间日期字符作为路径依据
        if (StringUtils.isBlank(dateString)) {
            long l = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            fmtDateString = sdf.format(l);
        } else {
            try {
                fmtDateString = formatDateString(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException("日期参数不合规!");
            }
        }

        // 文件名(不要后缀)为最底层路径
        String filename = article.getName();
        int i = filename.lastIndexOf(".");
        filename = filename.substring(0, i);

        String relativeSaveDir = fmtDateString + "/" + filename + "/";
        String separator = File.separator;
        // 替换成当前系统的路径分隔符
        if (!"/".equals(separator)) {
            relativeSaveDir.replace("/", separator);
        }


        return relativeSaveDir;
    }

    private String formatDateString(String dateString) throws ParseException {
        // java 貌似只支持将"2017/1/8"转换为标准日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        if (dateString.contains("-") || dateString.contains(".")) {
            dateString = dateString.replace("-", "/");
            dateString = dateString.replace(".", "/");
        }
        Date parse = sdf.parse(dateString);
        String fmtDateString = sdf.format(parse);
        return fmtDateString;
    }
}
