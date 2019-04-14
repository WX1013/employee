package com.emp.service.impl;

import com.emp.dao.DeptEntityMapper;
import com.emp.dao.EmpEntityMapper;
import com.emp.dao.UserEntityMapper;
import com.emp.pojo.DeptEntity;
import com.emp.pojo.EmpEntity;
import com.emp.pojo.EmpEntityExample;
import com.emp.pojo.LogEntity;
import com.emp.pojo.result.PageResult;
import com.emp.service.EmpService;
import com.emp.utils.CommonUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/15 14:14
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Resource
    private EmpEntityMapper empEntityMapper;

    @Resource
    private UserEntityMapper userEntityMapper;

    @Resource
    private DeptEntityMapper deptEntityMapper;

    @Resource
    private RedisTemplate<String, EmpEntity> redisTemplate;

    @Override
    public Integer save(EmpEntity entity) {
        int count;
        if (entity.getId() == null) { // 如果id为空，即为增加
            entity.setDelFlg(0);
            entity.setAddTime(new Date());
            entity.setUpdateTime(new Date());
            count = empEntityMapper.insertSelective(entity);
        } else { // 如果id不为空，即为修改
            entity.setUpdateTime(new Date());
            count = empEntityMapper.updateByPrimaryKey(entity);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        userEntityMapper.deleteByEmpId(id);
        empEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EmpEntity findOne(Integer id) {
        EmpEntity entity = empEntityMapper.selectByPrimaryKey(id);
        DeptEntity dept = deptEntityMapper.selectByPrimaryKey(entity.getDeptId());
        entity.setDeptName(dept == null ? "" : dept.getName());
        entity.setAddTimeStr(CommonUtil.date2String(entity.getAddTime(), "yyyy-MM-dd"));
        return entity;
    }

    @Override
    public PageResult findPage(EmpEntity emp, int pageNum, int pageSize) {
        // 判断缓存中是否有数据
        if (redisTemplate.opsForList().size("emps") > 0) {
            // 如果缓存中有数据，则从缓存中查询数据
            List<EmpEntity> range = redisTemplate.opsForList().range("emps", 0, -1);
            List<EmpEntity> pageResult = new ArrayList<>();
            int upCount = (pageNum - 1) * pageSize + pageSize;
            if (upCount >= range.size()) {
                upCount = range.size();
            }
            for (int i = (pageNum - 1) * pageSize; i < upCount; i++) {
                pageResult.add(range.get(i));
            }
            int total = range.size();
            int pages = total / pageSize + 1;
            return new PageResult(pageNum, pages, total, pageResult);
        } else {
            // 如果缓存中没有，则从数据库中查并且存数据到缓存中
            List<EmpEntity> emps = empEntityMapper.selectByExample(null);
            for (EmpEntity empEntity : emps) {
                redisTemplate.opsForList().rightPush("emps", empEntity);
            }
            PageHelper.startPage(pageNum, pageSize);
            EmpEntityExample example = new EmpEntityExample();
            EmpEntityExample.Criteria criteria = example.createCriteria();
            if (emp != null) {
                if (emp.getName() != null && emp.getName().length() > 0) {
                    criteria.andNameLike("%" + emp.getName() + "%");
                }
            }
            Page<EmpEntity> page = (Page<EmpEntity>) empEntityMapper.selectByExample(example);
            return new PageResult(pageNum, page.getPages(), page.getTotal(), page.getResult());
        }
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
