package com.emp.service.impl;

import com.emp.dao.LogEntityMapper;
import com.emp.pojo.LogEntity;
import com.emp.pojo.LogEntityExample;
import com.emp.pojo.result.PageResult;
import com.emp.service.LogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/27 15:26
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogEntityMapper logEntityMapper;

    @Override
    public PageResult findPage(LogEntity log,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LogEntityExample example = new LogEntityExample();
        LogEntityExample.Criteria criteria = example.createCriteria();
        if (log != null) {
            if (log.getUsername() != null && log.getUsername().trim().length() > 0) {
                criteria.andUsernameLike("%" + log.getUsername() + "%");
            }
        }
        Page<LogEntity> page=  (Page<LogEntity>) logEntityMapper.selectByExample(example);
        return new PageResult(pageNum,page.getPages(),page.getTotal(), page.getResult());
    }


    @Override
    public void save(LogEntity entity) {
        entity.setAddTime(new Date());
        logEntityMapper.insert(entity);
    }


}
