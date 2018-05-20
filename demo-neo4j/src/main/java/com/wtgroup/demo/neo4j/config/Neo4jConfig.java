package com.wtgroup.demo.neo4j.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * neo4j的配置类
 *
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-21-17:08
 */
@Configuration
@ComponentScan(basePackages = "com.wtgroup.demo.neo4j")     //totest springboot启动类的扫描有效吗? 还是说这里配置了才会注入neo4j的事务管理代码?
@EnableNeo4jRepositories(basePackages = "com.wtgroup.demo.neo4j.repository")        //激活neo4j仓库接口
@EnableTransactionManagement        //激活隐式事务管理
public class Neo4jConfig extends Neo4jConfiguration{


    @Bean   // 4.2.x下不能少了 Bean 注解
    public SessionFactory getSessionFactory() {
        //会自动加载 ogm.properties 文件, 使用其中的配置
        return new SessionFactory("com.wtgroup.demo.neo4j.domain");
    }

}
