package com.emp.controller;

import com.emp.pojo.FeedbackEntity;
import com.emp.pojo.UserEntity;
import com.emp.pojo.result.ApiResult;
import com.emp.pojo.result.PageResult;
import com.emp.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/** 反馈信息接口api
 * @Description:
 * @Author: Joker
 * @Date: 2019/4/3 11:12
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 添加用户反馈
     * @param entity
     * @return
     */
    @RequestMapping("/add")
    public ApiResult addFeedback(HttpServletRequest request, FeedbackEntity entity){
        if(entity == null || entity.getContent() == null || entity.getContent().trim().equals("")){
            return new ApiResult("500","请输入反馈内容");
        }
        UserEntity user = (UserEntity) request.getSession().getAttribute("user_info");
        if(user == null){
            return new ApiResult("300","未登陆，请登陆");
        }
        feedbackService.add(entity,user);
        return new ApiResult("200","反馈成功");
    }

    /**
     * 删除反馈信息
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public ApiResult delFeed(int id){
        feedbackService.delFeed(id);
        return new ApiResult("200","删除成功");
    }


    /**
     * 查询反馈信息
     * @param entity
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(FeedbackEntity entity, Integer page, Integer size){
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
        return feedbackService.search(entity,page,size);
    }

    /**
     * 采纳、不采纳反馈信息
     * @param id
     * @param state
     * @return
     */
    @RequestMapping("/updateState")
    public ApiResult updateState(int id, int state){
        feedbackService.updateState(id,state);
        return new ApiResult("200",state == 1 ? "采纳成功":"不采纳成功");
    }

}
