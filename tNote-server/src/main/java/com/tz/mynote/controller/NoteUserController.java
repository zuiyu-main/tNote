package com.tz.mynote.controller;

import com.alibaba.fastjson.JSONObject;
import com.tz.mynote.annotation.OptionalLog;
import com.tz.mynote.annotation.PassToken;
import com.tz.mynote.annotation.UserLoginToken;
import com.tz.mynote.bean.NoteUsers;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.service.NoteUserService;
import com.tz.mynote.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/login")
    @OptionalLog(module="用户", methods="登录接口")
    public ResultBean login( NoteUsers user){
        NoteUsers noteUsers=noteUserService.findByUserName(user);
        if(noteUsers==null){
            return ResultBean.builder().msg("登录失败,用户不存在").code(HttpStatus.OK.value()).build();
        }else {
            if (!noteUsers.getPassword().equals(user.getPassword())){
                return ResultBean.builder().msg("登录失败,密码错误").code(HttpStatus.OK.value()).build();
            }else {
                String token = null;
                try {
                    token = JwtUtil.getToken(noteUsers);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                JSONObject object = new JSONObject();
                object.put("id", noteUsers.getId());
                object.put("realName",noteUsers.getRealName());
                object.put("orgId",noteUsers.getOrgId());
                object.put("userName",noteUsers.getUserName());
                object.put("passWord",null);
                object.put("createTime",noteUsers.getGmtCreate());
                stringRedisTemplate.opsForValue().set(token,object.toString(),30,TimeUnit.MINUTES);
                Map<String,Object> map = new HashMap<>(8);
                map.put("token", token);
                map.put("user", object);
                return ResultBean.builder().msg("登录成功").code(HttpStatus.OK.value()).data(map).build();
            }
        }
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
    @PostMapping("/register")
    public ResultBean register(){
        return null;
    }

}
