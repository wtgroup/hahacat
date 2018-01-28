package com.wtgroup.jd.service;

import com.wtgroup.jd.dao.JdDao;
import com.wtgroup.jd.pojo.Product;
import com.wtgroup.jd.pojo.QueryVo;
import com.wtgroup.utils.paginationtag.Page;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-23-21:30
 */
@Service
public class JdServiceImpl implements JdService {
    @Autowired
    private JdDao jdDao;

    public Page<Product> queryProductListByVo(QueryVo queryVo) throws SolrServerException {
        return jdDao.queryProductListByVo(queryVo);
    }
}
