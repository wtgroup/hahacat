package com.wtgroup.utils.pcddata.factory;


import com.wtgroup.utils.pcddata.entity.Article;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

public interface ArticleFactory extends InitializingBean{


    public List<Article> getArticles();
}
