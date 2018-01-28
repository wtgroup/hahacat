package com.wtgroup.blog.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wtgroup.blog.exception.CustomSecurityException;
import com.wtgroup.blog.security.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import java.util.Properties;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-25-9:01
 */
@Configuration
@SuppressWarnings("deprecation")
public class WebConfiguration extends WebMvcConfigurerAdapter{

    @Value("${spring.velocity.charset:UTF-8}")  //引入主配置文件中的参数, 并且设置默认值为utf-8
    private String charset;

    @Bean
    @ConfigurationProperties(prefix = "spring.velocity")
    public VelocityConfig velocityConfig( ){
        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();

        Properties vp = new Properties();
        vp.put("input.encoding", charset);
        vp.put("output.encoding",charset);
        velocityConfigurer.setVelocityProperties(vp);
        return velocityConfigurer;
    }

    /**
     * 提供velocity的视图解析器
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.velocity")
    public VelocityViewResolver viewResolver( ){
        return new VelocityViewResolver();
    }

    
    
    
    /**
     * 校验器工厂
     * @return
     */
    @Bean
    public Validator validator( ){
        //默认配置文件: ValidationMessages.properties
        return new LocalValidatorFactoryBean();
    }
    
    
    
    /**
     * jackson视图
     */
    @Bean
    public MappingJackson2JsonView mappingJackson2JsonView( ){
        MappingJackson2JsonView view = new MappingJackson2JsonView();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        view.setObjectMapper(objectMapper);

        return view;
    }


    //重新addInterceptor() 将自定义拦截器注册进spring


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        SecurityInterceptor securityInterceptor = new SecurityInterceptor();
        registry.addInterceptor(securityInterceptor);
    }
}
