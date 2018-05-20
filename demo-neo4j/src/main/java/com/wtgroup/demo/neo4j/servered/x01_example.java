package com.wtgroup.demo.neo4j.servered;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-19-17:11
 */
public class x01_example implements AutoCloseable{

    //获取驱动
        //传入uri, 用户名, 密码
    //开启会话
    //执行事务任务

    private final Driver driver;

    public x01_example(String uri, String user, String password) {
        //获取驱动
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void printGreeting(final String message) {
        //开启一个会话
        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    StatementResult result = tx.run("CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("message", message));
                    return result.single().get(0).asString();
                }
            });
            System.out.println(greeting);
        }
    }

    public static void main(String... args) throws Exception {
        try (x01_example greeter = new x01_example("bolt://192.168.17.128:7687", "neo4j", "123456")) {
            greeter.printGreeting("hello, world");
        }
    }
}

