package com.wtgroup.utils.pcddata.paser.utils;


import com.wtgroup.utils.pcddata.commons.HTMLEscaper;
import com.wtgroup.utils.pcddata.constant.Profile;
import com.wtgroup.utils.pcddata.entity.MarkdownSlice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
    public MarkdownSlice preprocess(MarkdownSlice textSlice) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return super.preprocess(textSlice);
    }

    @Override
    public MarkdownSlice preprocessBlock_code(MarkdownSlice textSlice) {
        // TODO 实现代码块解析规则 totest

//        模板示例
//        <figure class="highlight plain">
//                <table>
//                    <tbody>
//                    <tr>
//                        <td class="gutter">
//                            <pre><span class="line">1</span><br><span class="line">2</span><br></pre>
//                        </td>
//                        <td class="code">
//                            <pre><span class="line">第一行代码</span><br>
//                                  <span class="line">第二行代码</span><br></pre>
//                        </td>
//                    </tr>
//                    </tbody>
//                </table>
//            </figure>


        String blockCodeTpl = "<figure class=\"highlight plain\">\n" +
                " <table>\n" +
                " <tr>\n" +
                " <td class=\"gutter\">\n" +
                " <pre></pre>\n" +
                " </td>\n" +
                " <td class=\"code\">\n" +
                " <pre></pre>\n" +
                " </td>\n" +
                " </tr>\n" +
                " </table>\n" +
                " </figure>\n";
        Document blockCodeDoc = Jsoup.parse(blockCodeTpl);
        String codeContent = textSlice.getContent();

        // 将代码内容按照换行符切割成数组
        /*这种处理策略, 尾部空行将被剔除掉*/
        String newLine = Profile.NEW_LINE;
        String[] codeLines = codeContent.split(newLine);
        // 遍历数组
        for (int i = 0; i < codeLines.length; i++) {
            // 生成行号
            String numSpan = "<span class=\"line\">" + (i+1) + "</span><br>";
            // 生成代码行
            String codeSpan = "<span class=\"line\">" + HTMLEscaper.escape(codeLines[i]) + "</span><br>";


            blockCodeDoc.select("pre").get(0).append(numSpan);
            blockCodeDoc.select("pre").get(1).append(codeSpan);
        }

        textSlice.setContent(blockCodeDoc.select("body").html());
        return textSlice;
    }
}
