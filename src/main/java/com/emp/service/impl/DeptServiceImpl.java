package com.emp.service.impl;

import com.emp.dao.DeptEntityMapper;
import com.emp.pojo.DeptEntity;
import com.emp.pojo.DeptEntityExample;
import com.emp.pojo.result.PageResult;
import com.emp.service.DeptService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/15 14:14
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptEntityMapper deptEntityMapper;

    @Override
    public PageResult findPage(DeptEntity dept, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        DeptEntityExample example=new DeptEntityExample();
        DeptEntityExample.Criteria criteria = example.createCriteria();
        if(dept!=null){
            if(dept.getName()!=null && dept.getName().length()>0){
                criteria.andNameLike("%"+dept.getName()+"%");
            }
        }
        Page<DeptEntity> page= (Page<DeptEntity>)deptEntityMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void save(DeptEntity entity) {
        if(entity.getId() == null){ // 如果id为空，即为增加
            deptEntityMapper.insertSelective(entity);
        }else{ // 如果id不为空，即为修改
            deptEntityMapper.updateByPrimaryKeySelective(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        DeptEntity entity = deptEntityMapper.selectByPrimaryKey(id);
        if(entity != null){
            // 修改删除标记为 1
            entity.setDelFlg(1);
            deptEntityMapper.updateByPrimaryKey(entity);
        }
    }

    @Override
    public DeptEntity findOne(Integer id) {
        return deptEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DeptEntity> findAll() {
        // 查询没有被删除的职工信息
        DeptEntityExample example = new DeptEntityExample();
        DeptEntityExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlgEqualTo(0);
        return deptEntityMapper.selectByExample(example);
    }

}
