package com.emp.controller;

import com.emp.pojo.LogEntity;
import com.emp.pojo.result.PageResult;
import com.emp.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 日志接口
 * @Author: Joker
 * @Date: 2019/2/27 15:26
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 获取日志列表
     * @return
     */
    @RequestMapping("/getLogList")
    public PageResult getLogList(LogEntity log,Integer page, Integer size){
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
        return logService.findPage(log,page, size);
    }

}
