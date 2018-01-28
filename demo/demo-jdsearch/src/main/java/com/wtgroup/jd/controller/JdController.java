package com.wtgroup.jd.controller;

import com.wtgroup.jd.pojo.Product;
import com.wtgroup.jd.pojo.QueryVo;
import com.wtgroup.jd.service.JdService;
import com.wtgroup.utils.paginationtag.Page;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.management.Query;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-23-20:24
 */
@Controller
public class JdController {

    @Autowired
    private JdService jdService;


    @RequestMapping("/list.action")
    public String list(QueryVo vo, Model model) throws SolrServerException {
        vo.getCurrentPage();
        Page<Product> page = jdService.queryProductListByVo(vo);

        model.addAttribute("page", page);
//        model.addAttribute(vo);
        if (vo.getQueryString().equals("*")) {
            model.addAttribute("queryString", "");
        } else {
            model.addAttribute("queryString", vo.getQueryString());
        }
        model.addAttribute("price", vo.getPrice());
        model.addAttribute("sort", vo.getSort());
        model.addAttribute("catalog_name", vo.getCatalog_name());
        return "list";
    }


}
