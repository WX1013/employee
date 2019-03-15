package com.emp.service;

import com.emp.pojo.UserEntity;
import com.emp.pojo.result.PageResult;

import java.util.List;

/**
 * @Description: 用户服务层
 * @Author: Joker
 * @Date: 2019/2/27 15:25
 */
public interface UserService {

    /**
     * 用户登录
     * @param username
     * @param password
     */
    Integer login(String username, String password);

    /**
     * 用户注册
     * @param username
     * @param password
     */
    Integer regist(String username, String password,String phone,String name);

    /**
     * 增加、修改
     */
    public void update(UserEntity user);


    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public UserEntity findOne(Integer id);


    /**
     * 批量删除
     * @param id
     */
    public void delete(Integer id);

    /**
     * 分页
     * @param pageNum 当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(UserEntity user, int pageNum,int pageSize);
}
