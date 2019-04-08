package com.emp.controller;

import com.emp.enums.HttpResultEnum;
import com.emp.pojo.EmpEntity;
import com.emp.pojo.UserEntity;
import com.emp.pojo.result.ApiResult;
import com.emp.pojo.result.PageResult;
import com.emp.service.EmpService;
import com.emp.service.UserService;
import com.emp.utils.CommonUtil;
import com.emp.utils.MailUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

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

    @Autowired
    private EmpService empService;

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
        Map<String,Object> map = userService.login(entity.getUsername(), entity.getPassword());
        Integer state = (Integer) map.get("code");
        if(state == 0){
            return new ApiResult("500","用户不存在");
        }else if(state ==1){
            return new ApiResult("500","用户名或密码错误");
        }else if (state == -1){
            return new ApiResult("500","用户已经被禁用，请联系管理员启用");
        }
        request.getSession().setAttribute("user_info",entity);
        return new ApiResult("200","登录成功");
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
        return new ApiResult("200","退出成功");
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
        boolean flag = CommonUtil.isPhone(phone);
        if(!flag){
            return new ApiResult("500","请输入正确的手机号码");
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

    @RequestMapping("/resetPass")
    public ApiResult resetPass(int id){
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setPassword("123456");
        userService.update(user);
        return new ApiResult("200","密码重置成功，密码为123456");
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

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @RequestMapping("/getUserInfo")
    public ApiResult getUserInfo(HttpServletRequest request){
        UserEntity user = (UserEntity) request.getSession().getAttribute("user_info");
        UserEntity userinfo = userService.getUserByUsername(user.getUsername());
        EmpEntity emp = empService.findOne(userinfo.getEmpId());
        return new ApiResult("200","获取成功",emp);
    }

    /**
     * 更新个人信息
     * @param entity
     * @return
     */
    @RequestMapping("/updateInfo")
    public ApiResult updateInfo(EmpEntity entity){
        if(entity != null) {
            empService.update(entity);
        }
        EmpEntity emp = empService.findOne(entity.getId());
        return new ApiResult("200","更新成功",emp);
    }

    /**
     * 修改密码
     * @param request
     * @param password
     * @param newpassword
     * @param repassword
     * @return
     */
    @RequestMapping("/changePass")
    public ApiResult changePass(HttpServletRequest request,String password,String newpassword,String repassword){
        UserEntity user = (UserEntity) request.getSession().getAttribute("user_info");
        if(user.getPassword().equals(password)){
            if(newpassword.equals(repassword)){
                userService.changePass(user.getUsername(),newpassword);
                return new ApiResult("200","修改成功,请重新登录");
            }else{
                return new ApiResult("500","两次密码输入不一致");
            }
        }else{
            return new ApiResult("500","原密码不正确");
        }
    }

    /**
     * 获取验证码,通过邮箱发送
     * @return
     */
    @RequestMapping("/getCode")
    public ApiResult getCode(String username,String email){
        // 获取验证码
        String randCode = CommonUtil.getRandCode();
        UserEntity loginUser = userService.getUserByUsername(username);
        if(loginUser == null){
            return new ApiResult("500","用户不存在");
        }
        EmpEntity emp = empService.findOne(loginUser.getEmpId());
        if(emp.getEmail() == null){
            return new ApiResult("500","您的个人信息尚未完善，请联系管理员重置密码\n联系方式：18855992252");
        }
        if(!emp.getEmail().equals(email)){
            return new ApiResult("500","邮箱错误，请重新输入");
        }
        // 发送验证码到用户邮箱
        MailUtils.sendMail(email,"您的验证码为：" + randCode,"验证码");
        return new ApiResult("200","发送成功",randCode);
    }

    /**
     * 忘记密码
     * @param username
     * @param newPassword
     * @return
     */
    @RequestMapping("/forgetPass")
    public ApiResult forgetPass(String username,String newPassword){
        userService.forgetPass(username,newPassword);
        return new ApiResult("200","找回密码成功，请登录");
    }

}
