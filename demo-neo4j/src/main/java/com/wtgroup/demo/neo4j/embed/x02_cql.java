package com.wtgroup.demo.neo4j.embed;

import com.wtgroup.demo.neo4j.common.CqlProcess;
import com.wtgroup.demo.neo4j.common.GraphDBFactory;
import org.junit.After;
import org.junit.Test;
import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * cypher查询条件
 * <p>
 * 1. 注意不同的遍历方式: 行遍历, 列遍历
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-19-10:24
 */
public class x02_cql {

    @Test
    public void fun01() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "first");
        params.put("skip", 0);
        params.put("limit", 5);
        findPageByName(params);
    }

    public Map<String, Object> findPageByName(Map<String, Object> params) {
        Map<String, Object> page = new HashMap<>();
        //拼接cql语句  根据 name 属性查询
        final String query = "match (n) where n.name=~{name} return n skip {skip} limit {limit}";

        //默认内嵌在事务中
        List<Map<String, Object>> data = GraphDBFactory.<List<Map<String, Object>>>cqlTemplate(new CqlProcess<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> process(Map<String, Object> params) {
                //存放分页数据
                List<Map<String, Object>> data = new ArrayList();
                Result result = graphDb.execute(query, params);
                //结果处理
                //按列取数
                ResourceIterator<Node> nCol = result.columnAs("n");//?和 query 语句中 n 对应?
                while (nCol.hasNext()) {
                    //定义存储数据行的map
                    Map<String, Object> record = new HashMap<>();
                    Node node = nCol.next();
                    record.put("id", node.getId());
                    record.put("name", node.getProperty("name"));
                    //放入 data 中
                    data.add(record);
                }

                return data;
            }
        }, params);


        page.put("data", data);

        try (Transaction tx = GraphDBFactory.getGraphDB().beginTx()) {
            String query1 = "match (n) where n.name =~ {name} return count(n) as count";
            Result result = GraphDBFactory.getGraphDB().execute(query1, params);
            //遍历行
            while (result.hasNext()) {
                //每一行就是一个map
                Object count = result.next().get("count");
                page.put("total", count);
            }

            tx.close();
        }
        return page;
    }



}
