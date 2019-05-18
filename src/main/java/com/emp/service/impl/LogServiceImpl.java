package com.emp.service.impl;

import com.emp.dao.LogEntityMapper;
import com.emp.pojo.LogEntity;
import com.emp.pojo.LogEntityExample;
import com.emp.pojo.result.PageResult;
import com.emp.service.LogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/27 15:26
 */
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogEntityMapper logEntityMapper;

    @Override
    public PageResult findPage(LogEntity log, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LogEntityExample example = new LogEntityExample();
        LogEntityExample.Criteria criteria = example.createCriteria();
        if (log != null) {
            if (log.getUsername() != null && log.getUsername().trim().length() > 0) {
                criteria.andUsernameLike("%" + log.getUsername() + "%");
            }
        }
        Page<LogEntity> page = (Page<LogEntity>) logEntityMapper.selectByExample(example);
        List<LogEntity> list = page.getResult();
        for (LogEntity entity : list) {
            // 模块
            String module = entity.getModule();
            if(module.contains("Dept")){
                entity.setModule("部门管理");
            }
            if(module.contains("Emp")){
                entity.setModule("职工管理");
            }
            if(module.contains("User")){
                entity.setModule("用户管理");
            }
            if(module.contains("Feed")){
                entity.setModule("反馈管理");
            }
            if(module.contains("Admin")){
                entity.setModule("管理员");
            }
            if(module.contains("Log")){
                entity.setModule("日志管理");
            }
            // 操作
            String method = entity.getMethod();
            if(method.contains("login")){
                entity.setMethod("登录");
            }
            if(method.equals("getUserInfo")){
                entity.setMethod("获取用户详细信息");
            }
            if(method.equals("updateInfo")){
                entity.setMethod("修改用户信息");
            }
            if(method.equals("search")){
                entity.setMethod("查询所有");
            }
            if(method.equals("getLogList")){
                entity.setMethod("获取日志列表");
            }
            if(method.equals("adminLogout")){
                entity.setMethod("退出登录");
            }
            if(method.equals("updateState")){
                entity.setMethod("禁用/启用用户");
            }
            if(method.equals("findOne")){
                entity.setMethod("获取职工详细信息");
            }
            if(method.equals("updateSalary")){
                entity.setMethod("设置工资");
            }
            if(method.equals("userLogout")){
                entity.setMethod("退出登录");
            }
            if(method.equals("getMembers")){
                entity.setMethod("获取部门成员");
            }
            if(method.equals("addMember")){
                entity.setMethod("添加部门成员");
            }
            if(method.equals("addFeedback")){
                entity.setMethod("发布反馈信息");
            }
            if(method.equals("findAll")){
                entity.setMethod("获取职工列表");
            }
            if(method.equals("save")){
                entity.setMethod("保存");
            }
            if(method.equals("delete")){
                entity.setMethod("删除");
            }
            if(method.equals("delFeed")){
                entity.setMethod("删除反馈");
            }
            if(method.equals("changePass")){
                entity.setMethod("修改密码");
            }
            if(method.equals("tobeLeader")){
                entity.setMethod("设置部长");
            }
            if(method.equals("resetPass")){
                entity.setMethod("重置密码");
            }
            if(method.equals("delMember")){
                entity.setMethod("删除部门成员");
            }
        }
        return new PageResult(pageNum, page.getPages(), page.getTotal(), page.getResult());
    }


    @Override
    public void save(LogEntity entity) {
        entity.setAddTime(new Date());
        logEntityMapper.insert(entity);
    }


}
