package com.emp.pojo.result;


import java.io.Serializable;
import java.util.List;

/**
 * @Author: Joker
 * @Date: 2019/2/13 10:48
 */
public class PageResult<T> implements Serializable{

    private Integer page;
    private Integer pages;
    private long total;
    private List<T> rows;

    public PageResult(Integer page,Integer pages,long total, List<T> rows) {
        this.page = page;
        this.pages = pages;
        this.total = total;
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
