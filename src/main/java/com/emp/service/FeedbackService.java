package com.emp.service;

import com.emp.pojo.FeedbackEntity;
import com.emp.pojo.UserEntity;
import com.emp.pojo.result.PageResult;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/4/3 11:08
 */
public interface FeedbackService {

    /**
     * 添加用户反馈
     * @param entity
     */
    void add(FeedbackEntity entity, UserEntity user);

    /**
     * 查询用户反馈
     * @param entity
     * @param page
     * @param size
     * @return
     */
    PageResult search(FeedbackEntity entity, Integer page, Integer size);

    /**
     * 删除反馈信息
     * @param id
     */
    void delFeed(int id);

    /**
     * 采纳、不采纳反馈信息
     * @param id
     * @param state
     */
    void updateState(int id, int state);
}
