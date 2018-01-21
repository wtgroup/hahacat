package com.wtgroup.comb.controller;

import com.wtgroup.comb.pojo.BaseDict;
import com.wtgroup.comb.pojo.vo.QueryVo;
import com.wtgroup.comb.service.BaseDictService;
import com.wtgroup.comb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-19-15:56
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private BaseDictService baseDictService;
    @Autowired
    private CustomerService customerService;

    @Value("${cust_source_type_code}")
    private String cust_source_type_code;
    @Value("${cust_industry_type_code}")
    private String cust_industry_type_code;
    @Value("${cust_level_type_code}")
    private String cust_level_type_code;


    @RequestMapping("/list.action")
    public String list(QueryVo queryVo, Model model) {

        //获取分页数据

        queryVo = customerService.queryPageByVo(queryVo);


        //获取下拉框数据
        //001：所属行业；002：客户来源；006：客户级别
        List<BaseDict> fromType = baseDictService.queryBaseDictListByTypeCode(cust_source_type_code);
        List<BaseDict> industryType = baseDictService.queryBaseDictListByTypeCode(cust_industry_type_code);
        List<BaseDict> levelType = baseDictService.queryBaseDictListByTypeCode(cust_level_type_code);

        queryVo.setFromType(fromType);
        queryVo.setIndustryType(industryType);
        queryVo.setLevelType(levelType);

        model.addAttribute("queryVo", queryVo);
        model.addAttribute("page", queryVo.getPage());
        model.addAttribute("customer", queryVo.getCustomer());

        //封装vo返回数据
        return null;
    }
}
