package com.emp.service;

import com.emp.pojo.AdminEntity;


/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/13 10:48
 */
public interface AdminService {

    /**
     * 根据名称查询管理员信息
     * @param name
     * @return
     */
    AdminEntity findByUsername(String name);

}
