package com.emp.service.impl;

import com.emp.dao.EmpEntityMapper;
import com.emp.pojo.EmpEntity;
import com.emp.pojo.EmpEntityExample;
import com.emp.pojo.result.PageResult;
import com.emp.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/15 14:14
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpEntityMapper empEntityMapper;

    @Override
    public void save(EmpEntity entity) {
        if(entity.getId() == null){ // 如果id为空，即为增加
            entity.setDelFlg(0);
            entity.setAddTime(new Date());
            entity.setUpdateTime(new Date() );
            empEntityMapper.insertSelective(entity);
        }else{ // 如果id不为空，即为修改
            empEntityMapper.updateByPrimaryKey(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        EmpEntity empEntity = empEntityMapper.selectByPrimaryKey(id);
        if(empEntity != null){
            empEntity.setDelFlg(1);
            empEntityMapper.updateByPrimaryKey(empEntity);
        }
    }

    @Override
    public EmpEntity findOne(Integer id) {
        return empEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(EmpEntity emp, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        EmpEntityExample example=new EmpEntityExample();
        EmpEntityExample.Criteria criteria = example.createCriteria();
        if(emp!=null){
            if(emp.getName()!=null && emp.getName().length()>0){
                criteria.andNameLike("%"+emp.getName()+"%");
            }
            if(emp.getIdentity()!=null && emp.getIdentity().length()>0){
                criteria.andIdentityLike("%"+emp.getIdentity()+"%");
            }
            if(emp.getAddress()!=null && emp.getAddress().length()>0){
                criteria.andAddressLike("%"+emp.getAddress()+"%");
            }
            if(emp.getPhone()!=null && emp.getPhone().length()>0){
                criteria.andPhoneLike("%"+emp.getPhone()+"%");
            }
            if(emp.getEmail()!=null && emp.getEmail().length()>0){
                criteria.andEmailLike("%"+emp.getEmail()+"%");
            }
        }
        Page<EmpEntity> page= (Page<EmpEntity>)empEntityMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
