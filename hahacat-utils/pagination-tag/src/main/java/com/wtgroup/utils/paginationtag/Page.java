package com.wtgroup.utils.paginationtag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * 分页查询用的bean.
 * 起始页号必须有默认值, 设为1, 分页查询时必须能够实现分页.
 * 即必须有起始行号和页大小. 否则, 当数据量太多时, 会一股脑输出(囧).
 *
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-03-19:47
 */
public class Page<T> {

    /**
     * 默认页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 5;
    // 前台输入:
    // 当前页
    private Integer currentPage;
    // 页大小
    private Integer pageSize;
    // 后台处理:
    // 总记录数
    private Long rowCount;
    // 当前页起始行, !mysql中起始为0!
    private Integer start;

    // 总页数
    private Integer pageCount;
    // 显示数据
    private List<T> rows;

    private Log logger = LogFactory.getLog(Page.class);

    // 页大小下拉框选择器(页面通过下拉选择数字指定每页显示的页大小) | pageSize的整数倍
//	private Integer[] pageSizeSelector = {3,6,9,12};


    /**
     * 不提供无参构造, 因为rowCount参数是必备的.
     * @param currentPage 当前页号
     * @param pageSize    每页行数,即页大小
     * @param rowCount    总行数
     */
    // 页面必须元素: 当期那页号, 总页数(构造内部计算), 进而需要总记录数和页大小
    // 所以以上参数必须传进来
    public Page(Integer currentPage, Integer pageSize, Long rowCount) {
        if (currentPage == null || currentPage <= 0) {
            setCurrentPage(1);
        }else{
            setCurrentPage(currentPage);
        }

        if (pageSize == null || pageSize <= 0) {
             setPageSize(DEFAULT_PAGE_SIZE);
        }else {
            setPageSize(pageSize);
        }


        // 计算出总页数
        if (rowCount == null || rowCount <= 0) {
            setRowCount(0L);
        }else {
            setRowCount(rowCount);
        }

        // 生成页大小选择器   --bug: 页大小一旦动态指定的, 那么这个数组最小值始终是动态指定的那个页大小
//		for (int i = 0; i < this.pageSizeSelector.length; i++) {
//			this.pageSizeSelector[i] = this.pageSize * (i+1);
//		}

    }

    /**
     * 计算出当前页起始索引
     *
     * @return
     */
    public Integer getStart() {
        //start 不为空说明可以直接return
        if (start != null) {
            return start;
        }
        if (currentPage == null || pageSize == null) {    //不满足计算的条件, 返回null
            return null;
        }
        return (this.currentPage - 1) * this.pageSize;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        //同步更新start
        if (this.currentPage != null && this.pageSize != null) {
            this.start = (this.currentPage - 1) * this.pageSize;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        if (this.currentPage != null && this.pageSize != null) {
            this.start = (this.currentPage - 1) * this.pageSize;
        }

    }


    public Long getRowCount() {
        return rowCount;
    }

    public void setRowCount(Long rowCount) {
        this.rowCount = rowCount;
        if (rowCount == null || rowCount <= 0) {
            logger.warn("请确保您的数据集确实只有0行, 否则, 将得不到预期的结果集!");
        }
        //同步更新总页数
        if (rowCount != null && this.pageSize!=null && this.pageSize>0) {
            this.pageCount = (this.rowCount.intValue() + this.pageSize - 1) / this.pageSize;

        }

    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
        //同步修正当前页, 防止当前页越界
        if (this.pageCount!=null && this.currentPage!=null && this.currentPage > this.pageCount) {
            this.currentPage = this.pageCount;
        }

    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
