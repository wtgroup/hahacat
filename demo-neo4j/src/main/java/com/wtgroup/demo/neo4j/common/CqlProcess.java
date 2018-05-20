package com.wtgroup.demo.neo4j.common;

import org.neo4j.graphdb.GraphDatabaseService;

import java.util.Map;

@FunctionalInterface
public interface CqlProcess<T>{
    GraphDatabaseService graphDb = GraphDBFactory.getGraphDB();
    /**
     * @param params 接受的参数
     * @return
     */
    public T process(Map<String,Object> params);

}

