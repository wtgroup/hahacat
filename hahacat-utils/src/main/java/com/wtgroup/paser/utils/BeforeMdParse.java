package com.wtgroup.paser.utils;


import com.wtgroup.paser.entity.MarkdownSlice;
import com.wtgroup.paser.entity.SliceTypeMap;
import com.wtgroup.paser.entity.TextSlice;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 调用md转HTML之前的预处理
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-18-19:18
 */
public class BeforeMdParse {

    private List<TextSlice> slices = new ArrayList<>();
    private static final String NEW_LINE = System.getProperty("line.separator");
    private SliceTypeMap types = MarkdownSlice.TYPES;

    // 传入md文件路径, 拆分出头信息....存入集合

    public Integer splitMd(String path) throws IOException {
        FileInputStream in = new FileInputStream(path);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(in, Charset.forName("UTF-8")));


        String normalSlice = "";
        String line;
        Integer lineCount = 0;
        Boolean headStart = false;  // 用来标记声明头是否开始
        HashMap<String, String> articleConfigs = new HashMap<>();
        while ((line = br.readLine()) != null) {
            line = line.trim();
            lineCount++;
            // 忽略空行
            if ("".equals(line)) {
                continue;
            }
            // 第一次读到"---"表示头声明开始了
            if (!headStart && "---".equals(line)) {
                headStart = true;
                // 提取头信息
                while ((line = br.readLine()) != null) {
                    lineCount++;
                    if ("---".equals(line.trim())) break;
                    // 分解出头参数信息, 键值对, 如: <title, 这是标题>
                    String key = line.substring(0, line.indexOf(":")).trim();
                    String value = line.substring(line.indexOf(":") + 1).trim();
                    articleConfigs.put(key, value);
                }
                // 忽略当前slice的结束标签, 如头声明的 "---"
                continue;
            }//end if


            // 其他需要特殊处理的slice
//            if (碰到需要特殊对待的slice) {
//                将已有的slice和对应的类型封装成对象, 并放入集合
//                while (知道碰到slice结束标记) {
//                    slice += line;
//                }
//                封装MarkdownSlice对象
//                add进slices中
//            }

            // 是否碰到特殊片段开始标记
            // 判断当前行是否是特殊slice
            // 是: 特殊处理
            if (types.containsStartTag(line)) {
                // 获取特殊标签对应的特殊类型名称
                String abnormalSliceType = (String) types.getByStartTag(line).getTypeName();
                String abnormalSliceEndTag = (String) types.getByStartTag(line).getEndTag();
                String abnormalContent = "";
                // 暂留当前的特殊标签符号, 用于判断结束
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    lineCount++;
                    if (abnormalSliceEndTag.equals(line)) break;
                    abnormalContent += line + NEW_LINE;
                }
                // 封装成MarkdownSlice对象并添加进集合中
                if (!"".equals(abnormalContent)) {
                    slices.add(new MarkdownSlice(abnormalSliceType, abnormalContent));
                }

                // 忽略当前slice的结束标签
                continue;

            }


            // 当碰到无需特殊处理的slice, 即normal类型的slice
            normalSlice += line + NEW_LINE;

            // 将normal类型的内容封装起来, 并存入集合
            // 封装成MarkdownSlice对象并添加进集合中   为了编程方便, 正常slice一行就是一个元素
            if (!"".equals(normalSlice)) {
                slices.add(new MarkdownSlice(MarkdownSlice.NORMAL, normalSlice));
                // normalSlice内容重置, 下次的normal类型的重新累加内容
                normalSlice = "";
            }

        }// end while


        // 文档读取结束后, 所有正常slice每一行就是slices中一个元素, 特殊slice批量封装成一个元素
        return lineCount;
    }

    public List<TextSlice> getSlices() {
        return slices;
    }

    public void setSlices(List<TextSlice> slices) {
        this.slices = slices;
    }
//    /**
//    }
//        }
//            return this;
//            }
//                slices.add(new MarkdownSlice(abnormalSliceType, abnormalContent));
//            if (!"".equals(abnormalContent)) {
//            // 封装成MarkdownSlice对象并添加进集合中
//            }
//                abnormalContent += line + NEW_LINE;
//                if( abnormalSliceLabel.equals(line) ) break;
//                lineCount++;
//            while ((line = br.readLine().trim()) != null) {
//            String abnormalContent = "";
//
//            }
//                normalSlice = "";
//                // normalSlice内容重置, 下次的normal类型的重新累加内容
//                slices.add(new MarkdownSlice(MarkdownSlice.NORMAL, normalSlice));
//            if (!"".equals(normalSlice)) {
//            // 封装成MarkdownSlice对象并添加进集合中
//            // 将normal类型的内容封装起来, 并存入集合
//        public PrepareAbnormalSlice invoke() throws IOException {
//
//        }
//            return lineCount;
//        public Integer getLineCount() {
//
//        }
//            return line;
//        public String getLine() {
//
//        }
//            return normalSlice;
//        public String getNormalSlice() {
//
//        }
//            this.abnormalSliceType = abnormalSliceType;
//            this.abnormalSliceLabel = abnormalSliceLabel;
//            this.lineCount = lineCount;
//            this.normalSlice = normalSlice;
//            this.br = br;
//        public PrepareAbnormalSlice(BufferedReader br, String normalSlice, Integer lineCount, String abnormalSliceLabel, String abnormalSliceType) {
//         */
//         * @param abnormalSliceType 对应的特殊类型
//         * @param abnormalSliceLabel 特殊片段的包裹字符. 实际运行中就是当前行内容(满足条件时)
//         * @param lineCount 行数
//         * @param normalSlice 正常片段内容
//         * @param br
//        /**
//
//        private String line;
//        private String abnormalSliceType;
//        private String abnormalSliceLabel;
//        private Integer lineCount;
//        private String normalSlice;
//        private BufferedReader br;
//    private class PrepareAbnormalSlice {
//     */
//     * 只要是相同字符开始和结束包裹的文本片段都可以利用这个内部类提取, 封装对象, 添加集合.


    /**
     * 测试是否可以将自定义Markdown, 借助3方包转成html
     *
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Test
    public void fun01() throws IOException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Integer lineCount = splitMd("D:\\Work\\JAVA\\workspace\\hahacat\\hahacat-web\\src\\test\\testpost01.md");
//        System.out.println(lineCount);
//        System.out.println(slices);
//        System.out.println(slices.size());
//        for (TextSlice slice : slices) {
//            System.out.print(slice.getContent());
//        }


        System.out.println("----------------------------");

        String html = ParseAndRender.fromList(slices);
        System.out.println(html);

    }
}
