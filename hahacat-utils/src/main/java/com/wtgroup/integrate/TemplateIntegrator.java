package com.wtgroup.integrate;

import com.wtgroup.entity.Article;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 将解析好的文章内容整合进3方模板
 */
public interface TemplateIntegrator {

    public void setTemplate(File htmlTemplate);

    public void setCharset(Charset charset);

    public Article integrate(Article article, File template) throws IOException;
    public List<Article> integrate(List<Article> articles, File template) throws IOException;

}
