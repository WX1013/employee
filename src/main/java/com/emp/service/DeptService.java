package com.emp.service;

import com.emp.pojo.DeptEntity;
import com.emp.pojo.result.PageResult;

import java.util.List;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/15 14:13
 */
public interface DeptService {

    /**
     * 分页
     * @param pageNum 当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(DeptEntity dept, int pageNum, int pageSize);

    /**
     * 保存部门
     * @param deptEntity
     */
    void save(DeptEntity deptEntity);

    /**
     * 删除部门
     * @param id
     */
    void delete(Integer id);

    /**
     * 查找一个部门信息
     * @param id
     * @return
     */
    DeptEntity findOne(Integer id);

    /**
     * 查询所有部门信息
     * @return
     */
    List<DeptEntity> findAll();

}
