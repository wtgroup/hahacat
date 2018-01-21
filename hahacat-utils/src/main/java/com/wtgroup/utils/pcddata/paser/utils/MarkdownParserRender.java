package com.wtgroup.utils.pcddata.paser.utils;

import com.wtgroup.utils.pcddata.entity.Markdown;
import com.wtgroup.utils.pcddata.entity.Article;

import java.util.List; /**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-22-1:40
 */
public interface MarkdownParserRender    {
    List<Article> parseAndRender(List<Markdown> markdowns);
}
