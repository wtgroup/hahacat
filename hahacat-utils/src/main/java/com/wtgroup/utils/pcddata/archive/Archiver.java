package com.wtgroup.utils.pcddata.archive;

import com.wtgroup.utils.pcddata.entity.Article;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 文章文章归档器
 */
public interface Archiver {


    public void archive(List<Article> articles, String publish);


    public Charset getCharset();

    public void setCharset(Charset charset);

}
