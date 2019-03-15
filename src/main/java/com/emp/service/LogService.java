package com.emp.service;

import com.emp.pojo.LogEntity;
import com.emp.pojo.result.PageResult;

/**
 * @Description: 日志服务层
 * @Author: Joker
 * @Date: 2019/2/27 15:25
 */
public interface LogService {

    /**
     * 返回分页列表
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 新增日志
     * @param entity
     */
    void save(LogEntity entity);

}
