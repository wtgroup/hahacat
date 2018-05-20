package com.wtgroup.demo.neo4j.embed;

import com.wtgroup.demo.neo4j.common.GraphDBFactory;
import com.wtgroup.demo.neo4j.common.NodeLabel;
import com.wtgroup.demo.neo4j.common.RelTypes;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;

import java.util.function.Consumer;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-17-18:44
 */
public class x01_基本api {

    @Test
    public void fun() {
        //createNoteRel();
        //update();
        //delete();
    }

    /**
     * 手动索引
     * i.e.每新增一个 node 接着添加至索引
     */
    @Test
    public void testIndex(){
        try (Transaction tx= graphDb.beginTx()){
            Index<Node> nodeIndex = graphDb.index().forNodes("ix_nodes");

            Node node = graphDb.createNode(NodeLabel.PERSON);
            node.setProperty("name","张三");
            //添加至索引
            nodeIndex.add(node,"name","张三");

            //有了索引, 就在索引中找
            IndexHits<Node> nodes = nodeIndex.get("name", "张三");
            Node single = nodes.getSingle();
            System.out.println(single.getProperty("name"));

            tx.success();
        }
    }

    @Test
    public void testSchemaIndex(){
        try (Transaction tx = graphDb.beginTx()) {
            //获取 schema
            Schema schema = graphDb.schema();
            IndexDefinition indexDefinition = schema.indexFor(NodeLabel.PERSON).on("name").create();
            System.out.println(indexDefinition);
        }
    }


    private void delete() {
        try (Transaction tx = graphDb.beginTx()) {
//        Node first = graphDb.findNode(NodeLabel.PERSON, "name", "first");
            Node node0 = graphDb.getNodeById(0L);
            Relationship rel = node0.getSingleRelationship(RelTypes.KNOWS, Direction.OUTGOING);
            rel.delete();
            Node endNode = rel.getEndNode();
            System.out.println("endNode: " + endNode);
            endNode.delete();
            node0.delete();

            tx.success();
        }
    }

    private void update() {
        try (Transaction tx1 = graphDb.beginTx()) {
            /*?怎么突然只能获取最新添加的node?*/
            ResourceIterator<Node> nodes = graphDb.findNodes(Label.label("Person"), "name", "second");
            //传统迭代方法
           /* while (nodes.hasNext()) {
                Node next = nodes.next();
                System.out.println("----" + next);
                System.out.println("name : " + next.getProperty("name"));
                //新增属性
                next.setProperty("age","19");
                next.setProperty("sex","男"+next.getId());
            }*/
            //nodes.close();

            //<Node> 指定了迭代元素的类型, 这样就可以使用 Node 的方法了
            Consumer<Node> consumer = (n) -> {
                System.out.println("------" + n);
                System.out.println("name: " + n.getProperty("name"));
                //新增属性
                n.setProperty("age", 19);
                n.setProperty("sex", "男" + n.getId() + "号");
            };

            nodes.stream().forEach(consumer);

            tx1.success();


            /*多个节点时, 会报: MultipleFoundException*/
            //Node node = graphDb.findNode(Label.label("Person"), "name", "second");
            ResourceIterator<Node> nodes1 = graphDb.findNodes(Label.label("Person"), "name", "second");
            System.out.println("更新之后:");
            while (nodes1.hasNext()) {
                Node next = nodes1.next();
                System.out.println("-----" + next);
                System.out.println(next.getProperty("sex"));
            }

        }

//        try (Transaction tx2 = graphDb.beginTx()) {
//            //PropertyValueFilteringNodeIdIterator
//            ResourceIterator<Node> nodes1 = graphDb.findNodes(Label.label("Person"), "name", "second");
//            while (nodes1.hasNext()) {
//                Node next = nodes1.next();
//                System.out.println("-----"+next.getId());
//                System.out.println(next.getProperty("sex"));
//            }
//        }

    }


    public void createNoteRel() {
        try (Transaction tx = graphDb.beginTx()) {  //try-with-resources
//            try-with-resources语句是一种声明了一种或多种资源的try语句。资源是指在程序用完了之后必须要关闭的对象。try-with-resources语句保证了每个声明了的资源在语句结束的时候都会被关闭。任何实现了java.lang.AutoCloseable接口的对象，和实现了java.io.Closeable接口的对象，都可以当做资源使用。
            Label person = Label.label("Person");
            Node node1 = graphDb.createNode(person);
            node1.setProperty("name", "first");

            Node node2 = graphDb.createNode(person);
            node2.setProperty("name", "second");

            Relationship knows = node1.createRelationshipTo(node2, RelationshipType.withName("KNOWS")); //还可以通过枚举类创建Relation视频Type
            knows.setProperty("desc", "知道,了解");

            System.out.println("node1's name is " + node1.getProperty("name"));
            System.out.println("node2's name is " + node2.getProperty("name"));
            System.out.println("relation type is : " + knows.getType());
            System.out.println("relation desc : " + knows.getProperty("desc"));

            tx.success();
        }
    }


    //public static final File DB_PATH = new File("D:\\DevelopKit\\neo4j-community-3.3.5\\data\\databases\\graph.db");
    private static GraphDatabaseService graphDb;


    /**
     * 初始化一个嵌入式图数据库
     */
    @Before
    public void getGraphDB() {
        //不会覆盖已有的物理数据
        //graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        //registerShutdownHook(graphDb);

        graphDb = GraphDBFactory.getGraphDB();
    }

    /**
     * 将关闭图数据库服务的任务作为钩子传入, 让jvm自己在合适的时机执行关闭操作.
     *
     * @param graphDb
     */
    private void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }


}
