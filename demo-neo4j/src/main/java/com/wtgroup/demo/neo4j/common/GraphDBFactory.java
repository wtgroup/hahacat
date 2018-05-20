package com.wtgroup.demo.neo4j.common;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**neo4j数据库工厂类
 * 开启/新建数据库
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-19-10:29
 */
public class GraphDBFactory {
    private static final File DB_PATH = new File("D:/DevelopKit/neo4j-community-3.3.5/data/databases/graph.db");
    /**
     * 单例
     * 线程间共享
     */
    private static GraphDatabaseService graphDb;

    /**
     * 初始化一个嵌入式图数据库
     */
    public static synchronized GraphDatabaseService getGraphDB() {
        //不会覆盖已有的物理数据
        if (graphDb==null) {
            graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        }
        waitToShutdown();
        return graphDb;
    }

    /**
     * 关闭数据库
     * 不是直接关闭, 而是清场之后在关闭
     */
    public static void waitToShutdown() {
        if (graphDb != null) {
            registerShutdownHook(graphDb);
        } else {
            System.out.println("当前没有活动的数据库实例");
        }
    }

    /**
     * 将关闭图数据库服务的任务作为钩子传入, 让jvm自己在合适的时机执行关闭操作.
     *
     * @param graphDb
     */
    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }


    /**删除数据库
     * @throws IOException
     */
    public void remove() throws IOException {
        FileUtils.deleteRecursively(DB_PATH);
    }


    /**查询方法模板
     * @param cqlProcess
     * @param params
     * @param <R>
     * @return
     */
    @Deprecated //麻烦
    public static <R> R cqlTemplate(CqlProcess<R> cqlProcess, Map<String,Object> params) {
        R result;
        try (Transaction tx = getGraphDB().beginTx()) {
            result = cqlProcess.process(params);

            tx.success();
        }

        return result;
    }


}
