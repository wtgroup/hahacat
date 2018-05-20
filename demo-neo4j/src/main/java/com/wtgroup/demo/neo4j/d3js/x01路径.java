package com.wtgroup.demo.neo4j.d3js;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;

import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;

/**neo4j D3.js
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-23-20:24
 */
public class x01路径 {
    public static final String URI="bolt://192.168.17.128:7687";
    public static final String USERNAME = "neo4j";
    public static final String PASSWORD = "123456";

    public static Driver driver;

    @Before
    public void getDriver(){
        driver = GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD));
    }

    @Test
    public void genJson(){
        Session session = driver.session();
        StatementResult result = session.run(
                "match p=allshortestpaths((n:Person{name:$name1})-[*..6]-(m:Person{name:$name2})) return p",
                parameters("name1", "Noah Wyle", "name2", "Joel Silver"));

        //转换数据成d3.js的格式

        while (result.hasNext()) {
            Record re = result.next();  //这里就一条记录: p, p下面有若干 path
            List<Value> paths = re.values();   //路径们
            for (Value p : paths) {
                System.out.println(p);  //PathValue  .adapted InternalPath
                //
                Path path = p.asPath();
                Iterable<Node> nodes = path.nodes();
                Iterable<Relationship> relationships = path.relationships();

                /*
                * node labels[] id properties[k-v,k-v,...]
                * relationship start end type id properties[k-v,k-v,...]*/
            }
        }

    }


}
