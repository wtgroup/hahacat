package com.wtgroup.utils.pcddata.commons;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * 站点核心配置文件处理器
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-24-23:54
 */
public class HhcatPropertiesHandler {


    /**
     * 对配置预处理(后期需要特殊预处理的参数只需要在这里添加即可).
     * 为了后面使用的灵活, 这里处理, 还是处理成Properties对象, String->String,
     * 只对一些需要做默认处理的值做处理.
     *
     * @param properties
     */
    public void handle(Properties properties) {
        assignDefaultProperties(properties);
    }

    private void assignDefaultProperties(Properties properties) {
        String publish = handlePublish(properties.getProperty("publish"));
        properties.setProperty("publish", publish);
        Charset charset = handleCharset(properties.getProperty("charset"));
        properties.setProperty("charset", charset.toString());
    }

    private String handlePublish(String publish) {
        String pub;
        if (StringUtils.isBlank(publish)) {
            // 默认为项目根目录下的publish文件夹
            pub = "publish";
        } else {
            pub = publish;
        }

        return pub;
    }


//    有的对象只给出了有参构造...太复杂, 这样一站式处理行不通
//    public Map<String, Object> handle() {
//        // 获取和处理sourceLocations
//        String[] sourceLocations = handleSourceLocations(properties.getProperty("sourceLocations"));
//        handledProperties.put("sourceLocations", sourceLocations);
//
//        // 获取和处理charset
//        Charset chs = handleCharset(properties.getProperty("charset"));
//        handledProperties.put("charset", chs);
//
//        // 创建slice预处理器
//        String markdownSlicePreprocessor = properties.getProperty("markdownSlicePreprocessor");
//        MarkdownSlicePreprocessor mdsp = new InstanceGetter<MarkdownSlicePreprocessor>().getInstanceForName(markdownSlicePreprocessor);
//        handledProperties.put("markdownSlicePreprocessor", mdsp);
//
//        // 生成模板文件
//        String articleTemplate = properties.getProperty("articleTemplate");
//        File articleTplFile = null;
//        try {
//            articleTplFile = handleArticleTemplate(articleTemplate);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        handledProperties.put("articleTemplate", articleTplFile);
//
//
//
//
//        return handledProperties;
//    }


    public String[] handleSourceLocations(String sl) {
        if (StringUtils.isBlank(sl)) {
            // 设置默认位置
            return new String[]{"source/_markdown"};
        }
        String[] sourceLocations = StringUtils.split(sl, ",");
        for (int i = 0; i < sourceLocations.length; i++) {
            sourceLocations[i] = sourceLocations[i].trim();
        }

        return sourceLocations;

    }

    public Charset handleCharset(String chs) {
        if (StringUtils.isBlank(chs)) {
            chs = "utf-8";
        }
        return Charset.forName(chs);
    }

    public File handleArticleTemplate(String tpl) {
        File template = null;
        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String webapproot = System.getProperty("webapp.root");
        if (StringUtils.isBlank(tpl)) {
            // 找到web根目录下的index.html作为模板文件
            // 备用: index.jsp
            template = new File(webapproot + "index.html");
            if (!template.exists()) {
                template = new File(webapproot + "index.jsp");
            }
        } else {
            template = new File(classpath+tpl);
        }
        if (template == null || !template.exists()) {
            throw new RuntimeException("找不到生成HTML文章的模板文件!");
        }

        return template;
    }


}