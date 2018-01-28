package com.wtgroup.jd.dao;

import com.wtgroup.jd.pojo.Product;
import com.wtgroup.jd.pojo.QueryVo;
import com.wtgroup.utils.paginationtag.Page;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-23-20:28
 */
@Repository
public class JdDaoImpl implements JdDao {
    @Autowired
    private SolrServer solrServer;


    public Page<Product> queryProductListByVo(QueryVo queryVo) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        //设置条件
        String queryString = queryVo.getQueryString();
        if (StringUtils.isBlank(queryString)) {
            //默认无关键字查询
            queryString = "*";
        }
        solrQuery.setQuery(queryString);
        solrQuery.set("df", "product_keywords");

        //添加过滤条件
        //分类筛选
        if (StringUtils.isNotBlank(queryVo.getCatalog_name())) {
            solrQuery.set("fq", "product_catalog_name:" + queryVo.getCatalog_name());
        }
        //价格区间
        String price = queryVo.getPrice();
        if (StringUtils.isNotBlank(price)) {
            String[] p = price.split("-");
            String rg = "[" + p[0] + " TO " + p[1] + "]";
            solrQuery.set("fq", "product_price:" + rg);
        }


        //排序
        String sort = queryVo.getSort();
        if (StringUtils.isNotBlank(sort) && sort.equals("1")) {
            solrQuery.addSort("product_price", SolrQuery.ORDER.asc);
        } else {
            solrQuery.addSort("product_price", SolrQuery.ORDER.desc);
        }

        //设置开始行和总行数
        //分页略
        solrQuery.setStart(0);  //当前页的起始行
        solrQuery.setRows(15);   //当前页大小

        //限定查询的域
        solrQuery.set("fl", "id,product_name,product_price,product_picture");

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
//        System.out.println("document总量:" + numFound);
        //拿出高亮容器
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        //初始化一个装产品信息的容器
        List<Product> products = new ArrayList<Product>();

        for (SolrDocument doc : docs) {

            Product product = new Product();
            try {
                product.setPid((String) doc.get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                product.setPicture((String) doc.get("product_picture"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                product.setName((String) doc.get("product_name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                product.setPrice((Float) doc.get("product_price"));
            } catch (Exception e) {
                e.printStackTrace();
            }


            //根据id获取对应的高亮内容
            try {
                Map<String, List<String>> map = highlighting.get(doc.get("id"));
                List<String> product_name = map.get("product_name");
                String product_name_hi = product_name.get(0);
                //如果有高亮名称, 则显示高亮名称, 否则显示非高亮名称
                //因为默认查询域是product_keywords  当product_name没有时高亮为null, 这时需要用原有的
                if (StringUtils.isNotBlank(product_name_hi)) {
                    product.setName(product_name_hi);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            products.add(product);
        }

        Page<Product> page = new Page<Product>(queryVo.getCurrentPage(), queryVo.getPageSize(), numFound);
        page.setRows(products);


        return page;
    }
}
