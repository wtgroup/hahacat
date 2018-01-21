package com.wtgroup.utils.pcddata.paser.utils;

import com.wtgroup.utils.pcddata.constant.Profile;
import com.wtgroup.utils.pcddata.entity.Markdown;
import com.wtgroup.utils.pcddata.entity.MarkdownSlice;
import com.wtgroup.utils.pcddata.entity.SliceTypeMap;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-22-10:58
 */
public class MarkdownPreprocessorImpl implements MarkdownPreprocessor {

    private SliceTypeMap<String, String, String> abTypeMap = Profile.ABNORMAL_MD_SLICETYPEMAP;
    private String newLine = Profile.NEW_LINE;

    public MarkdownPreprocessorImpl() {
    }

    /**
     * 遍历md集合, 对每一个md文件预处理.
     * 1. 分解不同片段(slice)
     * 2. set进Markdown实例中
     * 3. 得到预处理后的Markdown实例
     */
    public List<Markdown> preprocess(List<Markdown> markdowns, Charset charset, MarkdownSlicePreprocessor mdsp) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Markdown md : markdowns) {
            // 分解md, 获取头声明, 不同类型的片段, set进md实例
//            splitMd(md, charset);
            splitMarkdown(md, charset);
            // 判断是否要对slice进一预处理, 以实现嵌入前端框架样式
            // 是: 调用slice预处理器
            if (mdsp != null) {
                mdsp.preprocess(md.getSlices());
            }
        }

        return markdowns;
    }

    /**
     * 无需对slice进一步预处理, 且默认以utf8字符集
     *
     * @param markdowns
     * @param charset
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public List<Markdown> preprocess(List<Markdown> markdowns, Charset charset) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {

        preprocess(markdowns, charset, (MarkdownSlicePreprocessor) null);
        return markdowns;
    }

    public List<Markdown> preprocess(List<Markdown> markdowns) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {

        preprocess(markdowns, Charset.forName("utf-8"), (MarkdownSlicePreprocessor) null);
        return markdowns;
    }


    public void splitMarkdown(Markdown md, Charset charset) throws IOException {
        // 获取md文件源流
        InputStream src = md.getSrc();
        // 包装成BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(src, charset));

        // 存储正常文本
        String normalSlice = "";
        String line;
        Integer lineCount = 0;
        Boolean headStart = false;  // 用来标记声明头是否开始

        while ((line = br.readLine()) != null) {
            /*不要轻易trim, 比较的时候根据需求在trim*/
//            line = line.trim();
            lineCount++;
            // 空行折换成换行符
            if ("".equals(line)) {
                normalSlice += newLine;
            }

            // ==头声明参数处理==
            // 第一次读到"---"表示头声明开始了
            if (!headStart && "---".equals(line.trim())) {
                headStart = true;
                // 提取头信息
                while ((line = br.readLine()) != null) {
                    lineCount++;
                    line = line.trim();     // 文章配置信息需要trim
                    if ("---".equals(line)) break;
                    // 忽略空行
                    if ("".equals(line)) {
                        continue;
                    }
                    // 分解出头参数信息, 键值对, 如: <title, 这是标题>
                    String cfgKey = line.substring(0, line.indexOf(":")).trim();
                    String cfgValue = line.substring(line.indexOf(":") + 1).trim();
                    // 头声明配置处理
                    resolveHeadStateCfg(cfgKey, cfgValue, md);

                }
                // 忽略当前slice的结束标签, 如头声明的 "---"
                continue;
            }//end if


            String abnormalContent = "";


            // ==块级代码块==
            if ("```".equals(line.trim())) {
                while ((line = br.readLine()) != null) {
                    lineCount++;

                    // 再次碰到'```'=>代码块结束
                    if ("```".equals(line.trim())) {
                        break;
                    }
                    abnormalContent += line + newLine;
                }
                // 封装成MarkdownSlice对象并添加进集合中
                if (!"".equals(abnormalContent)) {
                    md.getSlices().add(new MarkdownSlice(Profile.BLOCK_CODE_SLICE, abnormalContent));
                    // abnormalContent重置
                    abnormalContent = "";
                }
                // 略过当前slice的结束标签
                continue;
            }


            // ==无序列表==
            // '* ', '- '打头
            if (line.startsWith("* ") || line.startsWith("- ")) {
                abnormalContent += line + newLine;
                while ((line = br.readLine()) != null) {
                    lineCount++;
                    if (line.startsWith("* ") || line.startsWith("- ")) {
                        abnormalContent += line + newLine;
                    } else {
                        break;
                    }
                }//end while
                // 封装成MarkdownSlice对象并添加进集合中
                if (!"".equals(abnormalContent)) {
                    md.getSlices().add(new MarkdownSlice(Profile.UNORDERED_LIST_SLICE, abnormalContent));
                    // abnormalContent重置
                    abnormalContent = "";
                }
                // 当前行指针指向了非无序列表的内容
            }//end if

            // TODO: 2018/1/4 其他需要特殊处理的slice请在这里补充代码


            // 当碰到无需特殊处理的slice, 即normal类型的slice
            normalSlice += line; // + newLine;

            // 将normal类型的内容封装起来, 并存入集合
            // 封装成MarkdownSlice对象并添加进集合中   为了编程方便, 正常slice一行就是一个元素
            if (!"".equals(normalSlice)) {
                md.getSlices().add(new MarkdownSlice(Profile.NORMAL_SLICE, normalSlice));
                // normalSlice内容重置, 下次的normal类型的重新累加内容
                normalSlice = "";
            }

        }// end while

        br.close();
        // 文档读取结束后, 所有正常slice每一行就是slices中一个元素, 特殊slice批量封装成一个元素
        md.setRows(lineCount);
    }


    public void splitMd(Markdown md, Charset charset) throws IOException {
        // 获取md文件源流
        InputStream src = md.getSrc();
        // 包装成BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(src, charset));

        // 存储正常文本
        String normalSlice = "";
        String line;
        Integer lineCount = 0;
        Boolean headStart = false;  // 用来标记声明头是否开始

        while ((line = br.readLine()) != null) {
            line = line.trim();
            lineCount++;
            // 忽略空行
//            if ("".equals(line)) {
//                continue;
//            }
            // 第一次读到"---"表示头声明开始了
            if (!headStart && "---".equals(line)) {
                headStart = true;
                // 提取头信息
                while ((line = br.readLine()) != null) {
                    lineCount++;
                    line = line.trim();
                    if ("---".equals(line)) break;
                    // 忽略空行
                    if ("".equals(line)) {
                        continue;
                    }
                    // 分解出头参数信息, 键值对, 如: <title, 这是标题>
                    String cfgKey = line.substring(0, line.indexOf(":")).trim();
                    String cfgValue = line.substring(line.indexOf(":") + 1).trim();
                    // 头声明配置处理
                    resolveHeadStateCfg(cfgKey, cfgValue, md);

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
            if (abTypeMap.containsStartTag(line)) {
                // 获取特殊标签对应的特殊类型名称
                String abnormalSliceType = (String) abTypeMap.getByStartTag(line).getTypeName();
                // 获得当前的特殊片段结束标签, 用于判断结束
                String abnormalSliceEndTag = (String) abTypeMap.getByStartTag(line).getEndTag();
                String abnormalContent = "";
                while ((line = br.readLine()) != null) {
                    lineCount++;
                    line = line.trim();
                    if (abnormalSliceEndTag.equals(line)) break;
                    // 注意不能忽略空行, 因为空行也是片段一部分, 有别于文件头和正常文本
                    abnormalContent += line + newLine;
                }
                // 封装成MarkdownSlice对象并添加进集合中
                if (!"".equals(abnormalContent)) {
                    md.getSlices().add(new MarkdownSlice(abnormalSliceType, abnormalContent));
                }

                // 忽略当前slice的结束标签
                continue;

            }


            // 当碰到无需特殊处理的slice, 即normal类型的slice
            normalSlice += line + newLine;

            // 将normal类型的内容封装起来, 并存入集合
            // 封装成MarkdownSlice对象并添加进集合中   为了编程方便, 正常slice一行就是一个元素
            if (!"".equals(normalSlice)) {
                md.getSlices().add(new MarkdownSlice(Profile.NORMAL_SLICE, normalSlice));
                // normalSlice内容重置, 下次的normal类型的重新累加内容
                normalSlice = "";
            }

        }// end while

        br.close();
        // 文档读取结束后, 所有正常slice每一行就是slices中一个元素, 特殊slice批量封装成一个元素
        md.setRows(lineCount);
    }

    /**
     * 头声明配置解析, 设置默认值, 处理, 判断是否在配置参数之内....
     * 最终将键值对存入所给的md文件的配置参数中
     *
     * @param cfgKey
     * @param cfgValue
     */
    private void resolveHeadStateCfg(String cfgKey, String cfgValue, Markdown md) {
        // 如果配置键在预定义的范围内
        if (Profile.MD_HEAD_STATE_CONFIG_KEYS.contains(cfgKey)) {

            // 对日期处理: 统一为'yyy/MM/dd'格式(Article类中将会处理), 这里仅设置缺省值, 后面要根据这个归档
            if (cfgKey.equals("date")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                if (StringUtils.isBlank(cfgValue)) {
                    long t = System.currentTimeMillis();
                    cfgValue = sdf.format(t);
                }
            }
            String[] cfgValues = resolveHeadSateCfgValue(cfgValue);
            // 存入配置属性map中
            md.getArticleConfigs().put(cfgKey, cfgValues);
        }


    }

    /**
     * 配置的值兼容单值和数组, 如, title:这是标题  or  title: [这是标题,这是标题,这是标题,..].
     * 但统一解析成字符数组形式.
     *
     * @param cfgValue
     * @return
     */
    private String[] resolveHeadSateCfgValue(String cfgValue) {
        cfgValue = cfgValue.trim();
        String[] cfgValues = {};
        // 兼容a [a,b,c] a,b,c 3种形式
        if (cfgValue.startsWith("[") && cfgValue.endsWith("]")) {
            cfgValue = cfgValue.replace("[", "").replace("]", "");
        }

        cfgValues = StringUtils.split(cfgValue, ",");
        for (int i = 0; i < cfgValues.length; i++) {
            cfgValues[i] = cfgValues[i].trim();
        }


        return cfgValues;
    }


}
