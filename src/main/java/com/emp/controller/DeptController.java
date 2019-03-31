package com.emp.controller;

import com.emp.enums.HttpResultEnum;
import com.emp.pojo.DeptEntity;
import com.emp.pojo.result.ApiResult;
import com.emp.pojo.result.PageResult;
import com.emp.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 部门接口api
 * @Author: Joker
 * @Date: 2019/2/15 14:15
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 增加、修改部门
     * @param entity
     * @return
     */
    @RequestMapping("/save")
    public ApiResult save(@RequestBody DeptEntity entity){
        if(entity != null){
            return new ApiResult();
        }
        return new ApiResult(HttpResultEnum.MIS_PARAM);
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public ApiResult delete(Integer id){
        if(id == null){
            return new ApiResult(HttpResultEnum.MIS_PARAM);
        }
        deptService.delete(id);
        return new ApiResult();
    }

    /**
     * 根据id查询一个部门的信息
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public ApiResult findOne(Integer id){
        if(id == null){
            return new ApiResult(HttpResultEnum.MIS_PARAM);
        }
        return new ApiResult(deptService.findOne(id));
    }

    /**
     * 查询所有部门信息
     * @return
     */
    @RequestMapping("/findAll")
    public ApiResult findAll(){
        return new ApiResult(deptService.findAll());
    }


    /**
     * 根据名称进行查询
     * @param dept
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody DeptEntity dept, Integer page, Integer size){
        return deptService.findPage(dept, page, size);
    }


}
