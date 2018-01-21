package com.wtgroup.demo.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.wtgroup.demo.domain.Article;
import com.wtgroup.demo.utils.Log;
import org.apache.struts2.convention.annotation.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2018-01-06-16:22
 */

public class ArticleAction extends CommonAction<Article> {


    @Action(value = "articleAction_doArchive")
    public String doArchive() {
        // session中获取articles
        List<Article> articles = (List<Article>) ActionContext.getContext().getSession().get("articles");
        if (articles == null) {
            articles = new ArrayList<Article>();
        }
        articles.add(getModel());
        return NONE;
    }


    @Action(value = "articleAction_findAll")
    public String findAll() {
        Log.begin();
        return NONE;
    }



}
