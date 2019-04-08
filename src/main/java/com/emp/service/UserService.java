package com.emp.service;

import com.emp.pojo.UserEntity;
import com.emp.pojo.result.PageResult;

import java.util.Map;

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
    Map<String,Object> login(String username, String password);

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

    /**
     * 启用、禁用用户
     * @param id
     * @param state
     */
    Integer updateState(Integer id, Integer state);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    UserEntity getUserByUsername(String username);

    /**
     * 修改密码
     * @param username
     * @param password
     */
    void changePass(String username, String password);

    /**
     * 找回密码
     * @param username
     * @param newPassword
     */
    void forgetPass(String username, String newPassword);
}
