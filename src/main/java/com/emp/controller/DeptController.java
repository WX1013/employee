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
    public ApiResult save(DeptEntity entity){
        if(entity != null){
            if(entity.getName() == null || (entity.getName()).trim().equals("")){
               return new ApiResult("500","部门不能为空");
            }
            Integer save = deptService.save(entity);
            if(save == 0){
                return new ApiResult("500","该部门已存在");
            }
            return new ApiResult("200","保存成功");
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
        return new ApiResult("200","删除成功");
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
     * 根据名称进行查询
     * @param dept
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(DeptEntity dept, Integer page, Integer size){
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
        return deptService.findPage(dept, page, size);
    }


}
