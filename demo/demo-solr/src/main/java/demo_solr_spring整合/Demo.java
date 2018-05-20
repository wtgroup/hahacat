package demo_solr_spring整合;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-02-07-19:08
 */
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class Demo {

    /**
     * 有spring注入 solrServer
     */
    @Resource
    private SolrServer solrServer;

    /**
     * SolrJ添加
     */
    @Test
    public void fun01() throws IOException, SolrServerException {


        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id","test_spring_solr");
        doc.setField("name","liuhejun,李冰冰");
        solrServer.add(doc);
        solrServer.commit();
    }



}
