package com.wtgroup.demo.demospringboot.config;

import com.wtgroup.demo.demospringboot.bean.AutoBean;
import com.wtgroup.demo.demospringboot.bean.ManualBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * 参考: https://juejin.im/post/5bcc54a9f265da0aaa054b3a
 */
@Slf4j
@Configuration
public class AutoBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //构造bean定义

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ManualBean.class);
        // 可以传入构造参数
        //beanDefinitionBuilder.addConstructorArgValue("自动注入依赖Bean");
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        registry.registerBeanDefinition("manualBean", beanDefinition);
    }

    /**有依赖属性的Bean, 也可以在这个方法里注册.
     * @param factory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
//        // 注册Bean实例，使用supply接口, 可以创建一个实例，并主动注入一些依赖的Bean；当这个实例对象是通过动态代理这种框架生成时，就比较有用了
//
//        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ManualBean.class, () -> {
//            // 构造实例
//            ManualBean manualBean = new ManualBean();
//            // 注入依赖属性
//
//
//            return manualBean;
//        });
//        BeanDefinition beanDefinition = builder.getRawBeanDefinition();
//        ((DefaultListableBeanFactory) factory).registerBeanDefinition("manualBean", beanDefinition);
    }
}
