package com.wtgroup.comb.pojo.vo;

import com.wtgroup.comb.pojo.BaseDict;
import com.wtgroup.comb.pojo.Customer;
import com.wtgroup.utils.paginationtag.Page;

import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-19-13:59
 */
public class QueryVo {

    private Customer customer;

    /**
     * 客户来源下拉列表
     */
    private List<BaseDict> fromType;

    /**
     * 客户所属行业下拉列表
     */
    private List<BaseDict> industryType;

    /**
     * 客户界别下拉列表
     */
    private List<BaseDict> levelType;
    
    /**
     * page数据
     */
    private Page<Customer> page;


    //分页导航传来的是两个独立参数: pageSize和currentPage
    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 当前页号
     */
    private Integer currentPage;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<BaseDict> getFromType() {
        return fromType;
    }

    public void setFromType(List<BaseDict> fromType) {
        this.fromType = fromType;
    }

    public List<BaseDict> getIndustryType() {
        return industryType;
    }

    public void setIndustryType(List<BaseDict> industryType) {
        this.industryType = industryType;
    }

    public List<BaseDict> getLevelType() {
        return levelType;
    }

    public void setLevelType(List<BaseDict> levelType) {
        this.levelType = levelType;
    }

    public Page<Customer> getPage() {
        return page;
    }

    public void setPage(Page<Customer> page) {
        this.page = page;
    }
}
