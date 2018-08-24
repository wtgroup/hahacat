package com.wtgroup.apachecsv.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/8/13 16:34
 */
public class UnicodeTest {

    /**
     * 修改字符串中的unicode码,将unicode编码转换成原来的字符
     * @param s 源str
     * @return  修改后的str
     */
    public static String decode2(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '\\' && chars[i + 1] == 'u') {
                char cc = 0;
                for (int j = 0; j < 4; j++) {
                    char ch = Character.toLowerCase(chars[i + 2 + j]);
                    if ('0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f') {
                        cc |= (Character.digit(ch, 16) << (3 - j) * 4);
                    } else {
                        cc = 0;
                        break;
                    }
                }
                if (cc > 0) {
                    i += 5;
                    sb.append(cc);
                    continue;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 获取修复后的字符串
     * @param str
     * @return
     */
    public static String getFixStr(String str) {
        Pattern p = Pattern.compile("(\\\\u.{4})");
        Matcher m = p.matcher(str);
        while (m.find()) {
            String xxx = m.group();
            str = str.replaceAll("\\" + xxx, decode2(xxx));
        }
        return str;
    }



    public static void main(String[] args) {
        //System.out.println(getFixStr("\\u003d\\u003d \\u0027 \\u003d \\u0027"));
        System.out.println(getFixStr("\u0001"));

    }
}
