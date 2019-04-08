package com.emp.service.impl;

import com.emp.dao.DeptEntityMapper;
import com.emp.dao.EmpEntityMapper;
import com.emp.pojo.DeptEntity;
import com.emp.pojo.EmpEntity;
import com.emp.pojo.EmpEntityExample;
import com.emp.pojo.result.PageResult;
import com.emp.service.EmpService;
import com.emp.utils.CommonUtil;
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
 * @Date: 2019/2/15 14:14
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpEntityMapper empEntityMapper;

    @Autowired
    private DeptEntityMapper deptEntityMapper;

    @Override
    public Integer save(EmpEntity entity) {
        int count;
        if(entity.getId() == null){ // 如果id为空，即为增加
            entity.setDelFlg(0);
            entity.setAddTime(new Date());
            entity.setUpdateTime(new Date() );
            count = empEntityMapper.insertSelective(entity);
        }else{ // 如果id不为空，即为修改
            entity.setUpdateTime(new Date());
            count = empEntityMapper.updateByPrimaryKey(entity);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        empEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EmpEntity findOne(Integer id) {
        EmpEntity entity = empEntityMapper.selectByPrimaryKey(id);
        DeptEntity dept = deptEntityMapper.selectByPrimaryKey(entity.getDeptId());
        entity.setDeptName(dept == null ? "" : dept.getName());
        entity.setAddTimeStr(CommonUtil.date2String(entity.getAddTime(),"yyyy-MM-dd"));
        return entity;
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
        return new PageResult(pageNum,page.getPages(),page.getTotal(), page.getResult());
    }

    @Override
    public void update(EmpEntity entity) {
        entity.setUpdateTime(new Date());
        empEntityMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<EmpEntity> findAll() {
        return empEntityMapper.selectByExample(null);
    }


}
