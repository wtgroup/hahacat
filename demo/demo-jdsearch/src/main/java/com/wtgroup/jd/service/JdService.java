package com.wtgroup.jd.service;

import com.wtgroup.jd.pojo.Product;
import com.wtgroup.jd.pojo.QueryVo;
import com.wtgroup.utils.paginationtag.Page;
import org.apache.solr.client.solrj.SolrServerException;

public interface JdService {

    public Page<Product> queryProductListByVo(QueryVo queryVo) throws SolrServerException;
}