package com.wtgroup.demo.demospringboot.config;

import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

/**项目全局配置
 *
 * 字段名和配置名一样, 可以不用加@Value("${appName}").
 *
 * 必须要有setter, 否则: Reason: Failed to bind properties under 'jfai.afs' to com.jfai.afs.mobiletag.config.AfsConfig: No setter found for property: charset
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/10/21 17:52
 */
@Component
@ConfigurationProperties(prefix = "jfai.afs")
@PropertySource(value="classpath:application.properties",encoding = "UTF-8",ignoreResourceNotFound=false)
public class ContextConfig {
    public static final org.slf4j.Logger log = LoggerFactory.getLogger(ContextConfig.class);

    /**
     * 项目启东时报告信息
     */
    @PostConstruct
    public void startupLog() {
        log.info("----App [{}] start to launch----",getAppName());

        //构建charset实例
        _charset = Charset.forName(charset);
        log.info("Global default charset is setted by '{}'",getCharset());

    }


    private String appName;
    private String charset;
    private Charset _charset;
//    private String rsaPubkey;
//    private String rsaPrivkey;
//    private String testName;


    public String getAppName() {
        return appName;
    }

    public ContextConfig setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public Charset getCharset() {
        //返回CharSet对象
        return _charset;
    }

    public ContextConfig setCharset(String charset) {
        this.charset = charset;
        return this;
    }


}
