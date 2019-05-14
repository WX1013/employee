package com.emp.service.impl;

import com.emp.dao.DeptEntityMapper;
import com.emp.dao.DeptMemberEntityMapper;
import com.emp.dao.EmpEntityMapper;
import com.emp.pojo.DeptEntity;
import com.emp.pojo.DeptMemberEntity;
import com.emp.pojo.DeptMemberEntityExample;
import com.emp.pojo.EmpEntity;
import com.emp.pojo.result.PageResult;
import com.emp.service.DeptMemberService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jdk.nashorn.internal.ir.EmptyNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/4/3 11:09
 */
@Service
public class DeptMemberServiceImpl implements DeptMemberService  {

    @Resource
    private DeptMemberEntityMapper deptMemberEntityMapper;

    @Resource
    private DeptEntityMapper deptEntityMapper;

    @Resource
    private EmpEntityMapper empEntityMapper;

    @Override
    public PageResult getDeptMembers(Integer deptId, Integer pageNum, Integer size) {
        PageHelper.startPage(pageNum,size);
        DeptMemberEntityExample example = new DeptMemberEntityExample();
        DeptMemberEntityExample.Criteria criteria = example.createCriteria();
        criteria.andDeptidEqualTo(deptId);
        Page<DeptMemberEntity> page= (Page<DeptMemberEntity>)deptMemberEntityMapper.selectByExample(example);
        List<DeptMemberEntity> list = page.getResult();
        for (DeptMemberEntity member : list) {
            EmpEntity emp = empEntityMapper.selectByPrimaryKey(member.getEmpid());
            member.setSalary(emp.getSalary());
            member.setPosition(emp.getPosition());
        }
        return new PageResult(pageNum,page.getPages(),page.getTotal(), page.getResult());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addMember(DeptMemberEntity entity) {
        // 获取该部门下有没有该职工
        List<DeptMemberEntity> members = deptMemberEntityMapper.selectByDeptId(entity.getDeptid());
        for (DeptMemberEntity member : members) {
            if(member.getEmpid() == entity.getEmpid()){
                return -1;
            }
        }
        // 获取添加的职工信息
        EmpEntity emp = empEntityMapper.selectByPrimaryKey(entity.getEmpid());
        entity.setEmpname(emp.getName());
        emp.setPosition("普通职工");
        // 给职工表添加部门信息
        emp.setDeptId(entity.getDeptid());
        empEntityMapper.updateByPrimaryKeySelective(emp);
        // 判断其他部门是否有该职工
        DeptMemberEntity oleEmp = deptMemberEntityMapper.selectByEmpId(entity.getEmpid());
        if(oleEmp != null){
            deptMemberEntityMapper.deleteByEmpId(entity.getEmpid());
        }
        // 获取部门信息
        DeptEntity dept = deptEntityMapper.selectByPrimaryKey(entity.getDeptid());
        entity.setDeptname(dept.getName());
        // 添加成员
        entity.setAddtime(new Date());
        entity.setUpdatetime(new Date());
        entity.setDelflg(0);
        int count = deptMemberEntityMapper.insertSelective(entity);
        return count;
    }

    @Override
    public void delMember(Integer id) {
        deptMemberEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void tobeLeader(Integer id) {
        DeptMemberEntity member = deptMemberEntityMapper.selectByPrimaryKey(id);
        DeptEntity dept = deptEntityMapper.selectByPrimaryKey(member.getDeptid());
        EmpEntity emp = empEntityMapper.selectByPrimaryKey(member.getEmpid());
        emp.setPosition(dept.getName()+"部长");
        empEntityMapper.updateByPrimaryKeySelective(emp);
    }
}
