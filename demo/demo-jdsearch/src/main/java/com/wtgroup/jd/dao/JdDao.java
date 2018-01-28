package com.wtgroup.jd.dao;

import com.wtgroup.jd.pojo.Product;
import com.wtgroup.jd.pojo.QueryVo;
import com.wtgroup.utils.paginationtag.Page;
import org.apache.solr.client.solrj.SolrServerException;

public interface JdDao {

    public Page<Product> queryProductListByVo(QueryVo queryVo) throws SolrServerException;

}
