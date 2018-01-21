package com.wtgroup.utils.pcddata.commons;

/**
 * HTML转义成普通文本. 即转成转义字符.
 * 如: '>' -> '&gt;'
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2018-01-05-13:49
 */
public class HTMLEscaper {

    public static String escape(String htmlText) {
        // < -> &lt;
        htmlText = htmlText.replace("<", "&lt;");
        // > -> &gt;
        htmlText = htmlText.replace(">", "&gt;");

        return htmlText;
    }

}
