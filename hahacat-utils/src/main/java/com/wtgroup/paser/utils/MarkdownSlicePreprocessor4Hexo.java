package com.wtgroup.paser.utils;


import com.wtgroup.paser.entity.TextSlice;

import java.lang.reflect.InvocationTargetException;

/**
 * 针对hexo框架解析的代码块样式设计的预处理器.
 *
 <figure class="highlight plain">
 <table>
 <tr>
 <td class="gutter">
 <pre><span class="line">1</span><br><span class="line">2</span><br></pre>
 </td>
 <td class="code">
 <pre><span class="line">public void fun()&#123;</span><br><span
 class="line">&#125;</span><br></pre>
 </td>
 </tr>
 </table>
 </figure>

 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-19-0:04
 */
public class MarkdownSlicePreprocessor4Hexo extends MarkdownSlicePreprocessor {


    @Override
    public TextSlice preprocess(TextSlice textSlice) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return super.preprocess(textSlice);
    }

    @Override
    public TextSlice preprocessCode(TextSlice textSlice) {
        // TODO 实现代码块解析规则

        String htmlcode = "<figure class=\"highlight plain\">\n" +
                " <table>\n" +
                " <tr>\n" +
                " <td class=\"gutter\">\n" +
                " <pre><span class=\"line\">1</span><br><span class=\"line\">2</span><br></pre>\n" +
                " </td>\n" +
                " <td class=\"code\">\n" +
                " <pre><span class=\"line\">public void fun()&#123;</span><br><span\n" +
                " class=\"line\">&#125;</span><br></pre>\n" +
                " </td>\n" +
                " </tr>\n" +
                " </table>\n" +
                " </figure>";

        textSlice.setContent(htmlcode);
        return textSlice;
    }
}
