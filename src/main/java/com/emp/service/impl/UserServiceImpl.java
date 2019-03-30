package com.emp.service.impl;

import com.emp.dao.EmpEntityMapper;
import com.emp.dao.UserEntityMapper;
import com.emp.pojo.EmpEntity;
import com.emp.pojo.UserEntity;
import com.emp.pojo.UserEntityExample;
import com.emp.pojo.result.PageResult;
import com.emp.service.UserService;
import com.emp.utils.CommonUtil;
import com.emp.utils.MD5Util;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/27 15:25
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private EmpEntityMapper empEntityMapper;

    @Override
    public Integer login(String username, String password) {
        UserEntity user = userEntityMapper.getUserByUsername(username);
        if (user == null) { // 没有此用户
            return 0;
        }
        if (MD5Util.md5(password).equals(user.getPassword())) {
            return 2; // 登录成功
        }
        return 1; // 用户名或者密码错误
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer regist(String username, String password, String phone, String name) {
        EmpEntity emp = new EmpEntity();
        emp.setName(name);
        emp.setPhone(phone);
        emp.setDelFlg(0);
        emp.setAddTime(new Date());
        emp.setUpdateTime(new Date());
        UserEntity userOld = userEntityMapper.getUserByUsername(username);
        if (userOld != null) {
            return 0;
        }
        UserEntity user = new UserEntity();
        user.setDelFlg(0);
        user.setAddTime(new Date());
        user.setUpdateTime(new Date());
        user.setUsername(username);
        user.setPassword(MD5Util.md5(password));
        int empId = empEntityMapper.insertSelective(emp);
        user.setEmpId(empId);
        userEntityMapper.insertSelective(user);
        return 1;
    }

    /**
     * 修改
     */
    @Override
    public void update(UserEntity user) {
        userEntityMapper.updateByPrimaryKey(user);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Integer id) {
        UserEntity user = userEntityMapper.selectByPrimaryKey(id);
        empEntityMapper.deleteByPrimaryKey(user.getEmpId()); // 删除职工表数据
        userEntityMapper.deleteByPrimaryKey(id); // 删除用户表数据
    }


    @Override
    public PageResult findPage(UserEntity user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserEntityExample example = new UserEntityExample();
        UserEntityExample.Criteria criteria = example.createCriteria();
        if (user != null) {
            if (user.getUsername() != null && user.getUsername().trim().length() > 0) {
                criteria.andUsernameLike("%" + user.getUsername() + "%");
            }
        }
        Page<UserEntity> page = (Page<UserEntity>) userEntityMapper.selectByExample(example);
        List<UserEntity> result = page.getResult();
        for (UserEntity userEntity : result) {
            userEntity.setAddTimeStr(CommonUtil.date2String(userEntity.getAddTime(),"yyyy-MM-dd"));
        }
        return new PageResult(pageNum,page.getPages(),page.getTotal(), result);
    }

    @Override
    public Integer updateState(Integer id, Integer state) {
        UserEntity user = userEntityMapper.selectByPrimaryKey(id);
        user.setDelFlg(state);
        EmpEntity emp = empEntityMapper.selectByPrimaryKey(user.getEmpId());
        emp.setDelFlg(state);
        int count1 = empEntityMapper.updateByPrimaryKey(emp);
        int count2 = userEntityMapper.updateByPrimaryKeySelective(user);
        if(count1 > 0 && count2 > 0){
            return 1;
        }
        return 0;
    }
}
