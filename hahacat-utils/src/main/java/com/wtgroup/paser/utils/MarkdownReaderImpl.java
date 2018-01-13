package com.wtgroup.paser.utils;

import com.wtgroup.entity.Markdown;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Markdown文件读取器
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-22-0:59
 */
public class MarkdownReaderImpl {
    // 存放md文件的路径们
    private String[] paths;
    private List<Markdown> markdowns = new ArrayList<Markdown>();

    public MarkdownReaderImpl(String[] paths) {
        this.paths = paths;

        // 现假设配置的web绝对路径
        String webapproot = System.getProperty("webapp.root");
        for (String path : paths) {
            // web绝对路径->物理磁盘绝对路径
            path = webapproot + path;
            File file = new File(path);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                readMarkdowns(files);
            }
        }

    }

    private void readMarkdowns(File[] files) {
        for (File file : files) {
            if (!file.exists()) {
                continue;
            }
            if (file.isDirectory()) {
                readMarkdowns(file.listFiles());
            } else {
                Markdown md = new Markdown(file.getAbsolutePath());
                markdowns.add(md);
            }
        }
    }


    // 遍历paths下的所有path, 在遍历其下的所有md文件, 生成Markdown类集合
    public List<Markdown> getMarkdownFiles() {
        if (markdowns != null) {
            return markdowns;
        } else {
            return null;
        }
    }
}
