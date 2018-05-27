package com.wtgroup.demo.hbase.utils;

/**知识图谱专用string utils
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/5/22 18:37
 */
public class KgStringUtils {



    /**根据ASCII码获取下一个字符串
     * @param current
     * @return
     * @date 2018-5-22 18:38:27
     */
    public static String nextString(String current)
    {
        Integer ascii = 0;
        StringBuffer sbu = new StringBuffer();
        char[] chars = current.toCharArray();
        char last = chars[chars.length - 1];
        chars[chars.length-1] = (char) ((int)last+1);
        for (char aChar : chars) {
            sbu.append(aChar);
        }

        return sbu.toString();
    }
}
