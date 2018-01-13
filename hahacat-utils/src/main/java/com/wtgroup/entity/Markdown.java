package com.wtgroup.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Markdown实体类.
 * 多例: 一个Markdown文件对应了一个Markdown实例
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-22-0:51
 */
public class Markdown extends File implements Text{
    // 存储原始的md文件流
    private InputStream src;
    // 存储文章配置参数, 源自头声明解析
    private Map<String, String[]> articleConfigs = new HashMap<String, String[]>();
    // Markdown正文各个片段
    private List<MarkdownSlice> slices = new ArrayList<MarkdownSlice>();
    // md文件总行数
    private int rows;

    public Markdown(String pathname) {
        super(pathname);
        try {
            this.src = new FileInputStream(pathname);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String[]> getArticleConfigs() {
        return articleConfigs;
    }

    public void setArticleConfigs(Map<String, String[]> articleConfigs) {
        this.articleConfigs = articleConfigs;
    }

    public List<MarkdownSlice> getSlices() {
        return slices;
    }

    public void setSlices(List<MarkdownSlice> slices) {
        this.slices = slices;
    }

    public InputStream getSrc() {
        return src;
    }

    public void setSrc(InputStream src) {
        this.src = src;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }



}
