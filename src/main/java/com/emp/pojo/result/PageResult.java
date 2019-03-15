package com.emp.pojo.result;


import java.io.Serializable;
import java.util.List;

/**
 * @Author: Joker
 * @Date: 2019/2/13 10:48
 */
public class PageResult<T> implements Serializable {

    private long total;

    private long page;

    private List<?> rows;
    public PageResult() {

    }
    public PageResult(long total, long page, List<?> rows) {
        super();
        this.total = total;
        this.page = page;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

}
