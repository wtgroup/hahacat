package com.wtgroup.factory;


import com.wtgroup.entity.Article;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

public interface ArticleFactory extends InitializingBean{


    public List<Article> getArticles();
}
