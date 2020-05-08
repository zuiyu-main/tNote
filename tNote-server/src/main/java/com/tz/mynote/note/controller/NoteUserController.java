package com.tz.mynote.note.controller;

import com.tz.mynote.annotation.OptionalLog;
import com.tz.mynote.annotation.PassToken;
import com.tz.mynote.annotation.UserLoginToken;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.common.dao.SaveService;
import com.tz.mynote.note.bean.vo.NoteUsersVO;
import com.tz.mynote.note.service.NoteUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tz
 * @Classname NoteUserController
 * @Description
 * @Date 2019-09-07 18:19
 */
@Api(value = "NoteUserController",tags = "用户登录管理")
@RestController
@RequestMapping("/user")
public class NoteUserController {
    @Autowired
    private NoteUserService noteUserService;

    /**
     * 登录
     * @param request
     * @param noteUsersVO
     * @return
     */
    @PostMapping("/login")
    @PassToken
    @OptionalLog(module="用户", methods="登录接口")
    public ResultBean login(HttpServletRequest request, @RequestBody NoteUsersVO noteUsersVO){
        return noteUserService.login(request,noteUsersVO);
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @UserLoginToken
    @OptionalLog(module="用户", methods="退出接口")
    public ResultBean logout(HttpServletRequest request){
        return noteUserService.logout(request);
    }
    /**
     * 注册用户
     * @param request
     * @param noteUsersVO
     * @return
     */
    @PassToken
    @PostMapping("/register")
    @OptionalLog(module="用户", methods="注册接口")
    public ResultBean register(HttpServletRequest request, @RequestBody @Validated(SaveService.class) NoteUsersVO noteUsersVO, BindingResult bindingResult){
        return noteUserService.register(request,noteUsersVO);
    }

    /**
     * 测试接口
     * @return
     */
    @PassToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }

    /**
     * 测试验证token接口
     * @return
     */
    @UserLoginToken
    @GetMapping("/get")
    public ResultBean get(){
        return ResultBean.builder().data("sssss").msg(HttpStatus.OK.getReasonPhrase()).code(HttpStatus.OK.value()).build();
    }



}
