package com.emp.service.impl;

import com.emp.dao.AdminEntityMapper;
import com.emp.pojo.AdminEntity;
import com.emp.pojo.AdminEntityExample;
import com.emp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/13 10:48
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminEntityMapper adminEntityMapper;

    @Override
    public AdminEntity findByUsername(String username) {
        AdminEntityExample example = new AdminEntityExample();
        AdminEntityExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<AdminEntity> admins = adminEntityMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(admins)){
            return admins.get(0);
        }
        return null;
    }
}
