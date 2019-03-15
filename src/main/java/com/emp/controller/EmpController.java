package com.emp.controller;

import com.emp.enums.HttpResultEnum;
import com.emp.pojo.EmpEntity;
import com.emp.pojo.result.ApiResult;
import com.emp.pojo.result.PageResult;
import com.emp.service.EmpService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 职工接口api
 * @Author: Joker
 * @Date: 2019/2/15 14:15
 */
@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 增加、删除职工
     * @param entity
     * @return
     */
    @RequestMapping("/save")
    public ApiResult save(@RequestBody EmpEntity entity){
        if(entity != null){
            return new ApiResult();
        }
        return new ApiResult(HttpResultEnum.MIS_PARAM);
    }

    /**
     * 删除职工
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public ApiResult delete(Integer id){
        if(id == null){
            return new ApiResult(HttpResultEnum.MIS_PARAM);
        }
        empService.delete(id);
        return new ApiResult();
    }

    /**
     * 根据id查询一个职工的信息
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public ApiResult findOne(Integer id){
        if(id == null){
            return new ApiResult(HttpResultEnum.MIS_PARAM);
        }
        return new ApiResult(empService.findOne(id));
    }

    /**
     * 查询+分页
     * @param emp
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody EmpEntity emp, int page, int size  ){
        return empService.findPage(emp, page, size);
    }
}
