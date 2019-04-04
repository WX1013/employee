package com.emp.controller;

import com.emp.enums.HttpResultEnum;
import com.emp.pojo.AdminEntity;
import com.emp.pojo.result.ApiResult;
import com.emp.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Description: 管理员接口Api
 * @Author: Joker
 * @Date: 2019/2/13 13:27
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     *
     * @param request
     * @param entity
     * @return
     */
    @RequestMapping("/login")
    public ApiResult adminLogin(HttpServletRequest request, @RequestBody AdminEntity entity) {
        // 登录失败
        AdminEntity adminEntity = adminService.findByUsername(entity.getUsername());
        if (adminEntity == null || !Objects.equals(adminEntity.getPassword(), entity.getPassword())) {
            // 账户密码错误
            return new ApiResult("400","账号或密码错误");
        }
        // 登录成功，把登录信息保存到session域中
        request.getSession().setAttribute("admin_info", adminEntity);
        return new ApiResult("200","登录成功");
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public ApiResult adminLogout(HttpServletRequest request) {
        // 删除session域中管理员登录信息
        request.getSession().removeAttribute("admin_info");
        return new ApiResult();
    }

}
