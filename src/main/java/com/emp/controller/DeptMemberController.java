package com.emp.controller;

import com.emp.pojo.DeptMemberEntity;
import com.emp.pojo.result.ApiResult;
import com.emp.pojo.result.PageResult;
import com.emp.service.DeptMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/4/3 11:12
 */
@RestController
@RequestMapping("/member")
public class DeptMemberController {

    @Autowired
    private DeptMemberService deptMemberService;

    /**
     * 获取部门成员
     * @param deptId
     * @return
     */
    @RequestMapping("/getMembers")
    public PageResult getMembers(Integer deptId, Integer page, Integer size){
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
        return deptMemberService.getDeptMembers(deptId,page,size);
    }

    /**
     * 添加部门成员
     * @param entity
     * @return
     */
    @RequestMapping("/addMember")
    public ApiResult addMember(DeptMemberEntity entity){
        Integer count = deptMemberService.addMember(entity);
        if(count == 1) {
            return new ApiResult("200", "添加成功");
        }else if(count == -1){
            return new ApiResult("500", "该部门下已有此职工");
        }
        return new ApiResult();
    }

    /**
     * 删除部门成员
     * @param id
     * @return
     */
    @RequestMapping("/delMember")
    public ApiResult delMember(Integer id){
        deptMemberService.delMember(id);
        return new ApiResult("200","刪除成功");
    }

    /**
     * 设置部长
     * @param id
     * @return
     */
    @RequestMapping("/tobeLeader")
    public ApiResult tobeLeader(Integer id){
        deptMemberService.tobeLeader(id);
        return new ApiResult("200","设置成功");
    }

}
