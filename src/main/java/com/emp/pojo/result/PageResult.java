package com.emp.pojo.result;


import java.io.Serializable;
import java.util.List;

/**
 * @Author: Joker
 * @Date: 2019/2/13 10:48
 */
public class PageResult<T> implements Serializable {

    private long total;
    private List<T> rows;

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
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
