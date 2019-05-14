package com.emp.service;

import com.emp.enums.HttpResultEnum;
import com.emp.pojo.EmpEntity;
import com.emp.pojo.result.PageResult;

import java.util.List;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/15 14:13
 */
public interface EmpService {

    /**
     * 添加、修改职工信息
     * @param entity
     */
    Integer save(EmpEntity entity);

    /**
     * 根据id删除职工
     * @param id
     */
    void delete(Integer id);

    /**
     * 根据id查找职工
     * @param id
     * @return
     */
    EmpEntity findOne(Integer id);

    /**
     * 分页
     * @param pageNum 当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(EmpEntity emp, int pageNum, int pageSize);

    /**
     * 修改职工信息
     * @param entity
     */
    void update(EmpEntity entity);

    List<EmpEntity> findAll();

    /**
     * 设置工资
     * @param emp
     * @return
     */
    int updateSalary(EmpEntity emp);
}
