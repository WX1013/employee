package com.emp.service.impl;

import com.emp.dao.FeedbackEntityMapper;
import com.emp.dao.UserEntityMapper;
import com.emp.pojo.FeedbackEntity;
import com.emp.pojo.FeedbackEntityExample;
import com.emp.pojo.UserEntity;
import com.emp.pojo.result.PageResult;
import com.emp.service.FeedbackService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/4/3 11:08
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackEntityMapper feedbackEntityMapper;

    @Resource
    private UserEntityMapper userEntityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(FeedbackEntity entity, UserEntity user) {
        UserEntity userInfo = userEntityMapper.getUserByUsername(user.getUsername());
        entity.setUserid(userInfo.getId());
        entity.setUsername(userInfo.getUsername());
        entity.setUserid(1);
        entity.setAddtime(new Date());
        entity.setState(0);
        feedbackEntityMapper.insertSelective(entity);
    }

    @Override
    public PageResult search(FeedbackEntity entity, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        FeedbackEntityExample example = new FeedbackEntityExample();
        FeedbackEntityExample.Criteria criteria = example.createCriteria();
        if (entity != null) {
            if (entity.getUsername() != null && entity.getUsername().trim().length() > 0) {
                criteria.andUsernameLike("%" + entity.getUsername() + "%");
            }
        }
        Page<FeedbackEntity> page = (Page<FeedbackEntity>) feedbackEntityMapper.selectByExample(example);
        List<FeedbackEntity> result = page.getResult();
        return new PageResult(pageNum,page.getPages(),page.getTotal(), result);
    }

    @Override
    public void delFeed(int id) {
        feedbackEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateState(int id, int state) {
        FeedbackEntity entity = new FeedbackEntity();
        entity.setId(id);
        entity.setState(state);
        feedbackEntityMapper.updateByPrimaryKeySelective(entity);
    }


}
