package com.emp.controller;

import com.emp.enums.HttpResultEnum;
import com.emp.pojo.UserEntity;
import com.emp.pojo.result.ApiResult;
import com.emp.pojo.result.PageResult;
import com.emp.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/27 15:30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param entity
     * @return
     */
    @RequestMapping("/login")
    public ApiResult login(HttpServletRequest request, @RequestBody UserEntity entity){
        if(entity == null){
            return new ApiResult(HttpResultEnum.MIS_PARAM);
        }
        Integer state = userService.login(entity.getUsername(), entity.getPassword());
        if(state == 0){
            return new ApiResult("500","用户不存在");
        }else if(state ==1){
            return new ApiResult("500","用户名或密码错误");
        }
        request.getSession().setAttribute("user_info",entity);
        return new ApiResult();
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public ApiResult userLogout(HttpServletRequest request){
        // 删除session域中用户信息
        request.getSession().removeAttribute("user_info");
        return new ApiResult();
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/regist")
    public ApiResult regist(String username,String password,String phone,String name){
        if(username == null || password == null || phone == null){
            return new ApiResult(HttpResultEnum.MIS_PARAM);
        }
        Integer result = userService.regist(username, password,phone,name);
        if(result == 0){
            return new ApiResult("500","用户名已被占用");
        }
        return new ApiResult("200","注册成功");
    }

    @RequestMapping("/updateState")
    public ApiResult updateState(Integer id, Integer state){
        Integer count = userService.updateState(id, state);
        if (count == 1) {
            if(state == 1){
                return new ApiResult("200","禁用成功");
            }else{
                return new ApiResult("200","启用成功");
            }
        }
        return new ApiResult("400","操作失败");
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @RequestMapping("/update")
    public ApiResult update(@RequestBody UserEntity user){
        try {
            userService.update(user);
            return new ApiResult("200", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult("400", "修改失败");
        }
    }

    /**
     * 批量删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public ApiResult delete(Integer id){
        try {
            userService.delete(id);
            return new ApiResult("200", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult("400", "删除失败");
        }
    }

    /**
     * 查询+分页
     * @param user
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(UserEntity user, Integer page, Integer size ){
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
        return userService.findPage(user, page, size);
    }

}
