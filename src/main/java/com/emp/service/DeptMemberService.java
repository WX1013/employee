package com.emp.service;

import com.emp.pojo.DeptMemberEntity;
import com.emp.pojo.result.PageResult;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/4/3 11:09
 */
public interface DeptMemberService {

    /**
     * 获取小组成员列表
     * @param deptId
     * @param page
     * @param size
     * @return
     */
    PageResult getDeptMembers(Integer deptId,Integer page,Integer size);

    /**
     * 添加部门成员
     * @param entity
     */
    Integer addMember(DeptMemberEntity entity);

    /**
     * 刪除部门成员
     * @param id
     */
    void delMember(Integer id);

    /**
     * 设置成部长
     * @param id
     */
    void tobeLeader(Integer id);
}
