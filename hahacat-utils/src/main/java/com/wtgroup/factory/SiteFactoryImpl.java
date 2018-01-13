package com.wtgroup.factory;

import com.wtgroup.archive.Archiver;
import com.wtgroup.commons.HhcatPropertiesHandler;
import com.wtgroup.commons.InstanceGetter;
import com.wtgroup.commons.ResourceUtils;
import com.wtgroup.constant.Profile;
import com.wtgroup.entity.Article;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 站点生成工厂类
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-22-0:26
 */
public class SiteFactoryImpl implements SiteFactory {


    // 持有ArticleFactory
    private ArticleFactory af;
    // 持有建档器
    private Archiver archiver;
    private Properties properties;


    // 配置文件路径
    private String hhcatPropertiesLocation;
    // 处理后的配置参数map
    private Map<String, Object> handledProperties;

    public void setHhcatPropertiesLocation(String hhcatPropertiesLocation) {
        this.hhcatPropertiesLocation = hhcatPropertiesLocation;
    }
    //

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
        siteBuild();
    }

    private void siteBuild() {
        // 利用ArticleFactory生成所需的站点文章
        List<Article> articles = af.getArticles();
        // 根据文章参数信息, 归档, 存盘, 配置链接....
        archiver.archive(articles, properties.getProperty("publish"));

    }

    // 做些准备工作
    private void init() {
        // 读取配置文件, 获取配置参数
        properties = loadHhcatProperties(hhcatPropertiesLocation);
        // 同步到Profile中
        Profile.HHCATPROPERTIES = properties;
        HhcatPropertiesHandler handler = new HhcatPropertiesHandler();
        // 对配置预处理(后期需要特殊预处理的参数只需要在这里添加即可)
        handler.handle(properties);

        // 创建ArticleFactory
        af = new ArticleFactoryImpl(properties);

        // 创建归档工具类
        try {
            archiver = new InstanceGetter<Archiver>().getInstanceForName(properties.getProperty("archiver"));
            archiver.setCharset(Charset.forName(properties.getProperty("charset")));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("反射创建'archiver'失败! 或没有配置'archiver'参数.");
        }
        //


    }


    private Properties loadHhcatProperties(String hhcatPropertiesLocation) {
//        ClassLoader classLoader = SiteFactoryImpl.class.getClassLoader();
        Properties properties = null;
        // 获取核心配置文件输入流
//        InputStream propertiesIn = classLoader.getResourceAsStream(hhcatPropertiesLocation);
        InputStream propertiesIn = null;
        try {
            propertiesIn = ResourceUtils.getInputStreamForPath(hhcatPropertiesLocation);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to obtain input stream for cacheManagerConfigFile [" + hhcatPropertiesLocation + "]");
        }
        try {
            // 加载配置参数
            properties = new Properties();
            properties.load(propertiesIn);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("加载配置文件失败!");
        }

        return properties;
    }


}



