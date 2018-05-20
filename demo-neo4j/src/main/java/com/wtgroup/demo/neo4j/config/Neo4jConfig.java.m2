package com.wtgroup.demo.neo4j.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * neo4j的配置类
 * 整合 ogm.properties 至 application.properties中
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-21-17:08
 */
@Configuration
//@ComponentScan(basePackages = "com.wtgroup.demo.neo4j")     //totest springboot启动类的扫描有效吗? 还是说这里配置了才会注入neo4j的事务管理代码?
@EnableNeo4jRepositories(basePackages = "com.wtgroup.demo.neo4j.repository")        //激活neo4j仓库接口
@EnableTransactionManagement        //激活隐式事务管理
public class Neo4jConfig {

    @Value("${neo4j.ogm.driver}")  //引入主配置文件中的参数
    private String driverName;
    @Value("${neo4j.ogm.URI}")
    private String URI;
    @Value("${neo4j.ogm.username}")
    private String username;
    @Value("${neo4j.ogm.password}")
    private String password;


    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
//        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration("ogm.properties");
        config.driverConfiguration()
                .setDriverClassName(driverName)
                .setURI(URI)
                .setCredentials(username, password);
        return config;
    }

    @Bean   //没有亦可
    public SessionFactory getSessionFactory() {
//        //想要上文的配置生效, 必须传入进来
//        //没有配置类, 则使用 'ogm.properties' 自动创建
        return new SessionFactory(getConfiguration(), "com.wtgroup.demo.neo4j.domain");
        //使用 'ogm.properties' 自动创建
//        return new SessionFactory("com.wtgroup.demo.neo4j.domain");
    }

}
