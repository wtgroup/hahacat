package com.wtgroup.solrj.demo;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import javax.print.Doc;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-23-15:08
 */
public class Demo {


    private SolrServer solrServer;

    @Before
    public void connectSolr( ){
        // 默认会走核1
//        String baseUrl = "http://localhost:8080/solr";
        String baseUrl = "http://localhost:8080/solr/collection1";
        solrServer = new HttpSolrServer(baseUrl);
    }

    /**
     * SolrJ添加
     */
    @Test
    public void fun01() throws IOException, SolrServerException {


        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id","add_test");
        doc.setField("name","李冰冰");
        solrServer.add(doc);
        solrServer.commit();
    }

    /**
     * SolrJ查询
     */
    @Test
    public void fun02() throws SolrServerException {

        SolrQuery solrQuery = new SolrQuery();
        //设置条件
//        solrQuery.set("q", "*:*");
        solrQuery.setQuery("花儿朵朵");
        solrQuery.set("df", "product_name");
        //添加过滤条件
        solrQuery.set("fq", "product_price:[3 TO 20}");

        //排序
        solrQuery.addSort("product_price", SolrQuery.ORDER.desc);

        //设置开始行和总行数
        solrQuery.setStart(0);
        solrQuery.setRows(5);

        //限定查询的域
        solrQuery.set("fl", "id,product_name,product_price");

        //高亮
        //开启高亮
        solrQuery.setHighlight(true);
        //指定高亮域
        solrQuery.addHighlightField("product_name");
        //设置HTML标签前后缀
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");

        QueryResponse response = solrServer.query(solrQuery);

        SolrDocumentList docs = response.getResults();
        long numFound = docs.getNumFound();
        System.out.println("document总量:" + numFound);
        //拿出高亮容器
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        for (SolrDocument doc : docs) {
            System.out.println(doc.get("id"));
            System.out.println(doc.get("product_name"));
            System.out.println(doc.get("product_picture"));
            System.out.println(doc.get("product_catalog_name"));
            System.out.println(doc.get("product_price"));
            System.out.println("--------------------");

            //根据id获取对应的高亮内容
            Map<String, List<String>> map = highlighting.get(doc.get("id"));
            List<String> product_name = map.get("product_name");
            System.out.println(product_name.get(0));

            System.out.println("================================");
        }


    }



}
