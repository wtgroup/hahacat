package com.wtgroup.demo.sbproxy.config;

import lombok.Setter;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/12/18 1:31
 */
@Setter
@Configuration
@ConfigurationProperties(prefix = "proxy")
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8", ignoreResourceNotFound = false)
public class ProxyServletConfiguration { // implements EnvironmentAware {
    @Value("${proxy.servlet-url-one}")
    private String servletUrlOne;
    @Value("${proxy.target_url_one}")
    private String targetUrlOne;
    @Value("${proxy.servlet_url_two}")
    private String servletUrlTwo;
    @Value("${proxy.target_url_two}")
    private String targetUrlTwo;

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), servletUrlOne);
        //这个setName必须要设置，并且多个的时候，名字需要不一样
        servletRegistrationBean.setName("suitone");
        servletRegistrationBean.addInitParameter("targetUri", targetUrlOne);
        servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, "true");
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean2(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), servletUrlTwo);
        //这个setName必须要设置，并且多个的时候，名字需要不一样
        servletRegistrationBean.setName("suittwo");
        servletRegistrationBean.addInitParameter("targetUri", targetUrlTwo);
        servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, "true");
        return servletRegistrationBean;
    }



//    @Override
//    public void setEnvironment(Environment environment) {
    // to test --FAIL
//        Binder binder = Binder.get(environment);
//        //User u = binder.bind("wtgroup", Bindable.ofInstance(new User())).get();
//        BindResult<User> br = binder.bind("wtgroup", Bindable.of(User.class));
//        User u = br.get();
//        System.out.println(u);
//}

//    @Data
//    @ToString
//    public static class User{
//        private String name;
////        private String skillA;
////        private String skillB;
//
//    }
}
