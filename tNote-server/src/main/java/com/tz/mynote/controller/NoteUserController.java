package com.tz.mynote.controller;

import com.alibaba.fastjson.JSONObject;
import com.tz.mynote.annotation.OptionalLog;
import com.tz.mynote.annotation.PassToken;
import com.tz.mynote.annotation.UserLoginToken;
import com.tz.mynote.bean.NoteUsers;
import com.tz.mynote.bean.VO.NoteUsersVO;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.service.NoteUserService;
import com.tz.mynote.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author tz
 * @Classname NoteUserController
 * @Description
 * @Date 2019-09-07 18:19
 */
@RestController
@RequestMapping("/user")
public class NoteUserController {
    @Autowired
    private NoteUserService noteUserService;

    @PostMapping("/login")
    @PassToken
    @OptionalLog(module="用户", methods="登录接口")
    public ResultBean login(HttpServletRequest request, @RequestBody NoteUsersVO noteUsersVO){
        return noteUserService.login(request,noteUsersVO);
    }
    @PassToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }
    @UserLoginToken
    @GetMapping("/get")
    public ResultBean get(){
        return ResultBean.builder().data("sssss").msg(HttpStatus.OK.getReasonPhrase()).code(HttpStatus.OK.value()).build();
    }

    /**
     * 注册用户
     * @param request
     * @param noteUsersVO
     * @return
     */
    @PostMapping("/register")
    public ResultBean register(HttpServletRequest request,@RequestBody NoteUsersVO noteUsersVO){
        return noteUserService.register(request,noteUsersVO);
    }

}
