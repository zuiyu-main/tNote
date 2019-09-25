package com.tz.mynote.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.source.doctree.EndElementTree;
import com.tz.mynote.bean.NoteUsers;
import com.tz.mynote.constant.Login;
import com.tz.mynote.dao.NoteUsersMapper;
import com.tz.mynote.service.NoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tz
 * @Classname LoginInfoUtil
 * @Description 登录信息获取
 * @Date 2019-09-22 21:12
 */
@Slf4j
@Component
public class LoginInfoUtil {
    @Autowired
    private NoteUserService noteUserService;
    /**
     * 根据token获取登录信息
     * @param request
     * @return
     */
    public NoteUsers getLoginInfo(HttpServletRequest request){
        try {
            String token = request.getHeader(Login.AUTHORIZATION);
            DecodedJWT jwt = JWT.decode(token);
            String  userId = jwt.getAudience().get(0);
            NoteUsers user = noteUserService.findUserById(userId);
            log.debug("根据token获取登录信息,token={},userId={},user={}",token,userId,user.toString());
            return user;
        } catch (JWTDecodeException j) {
            log.error("token获取登录信息出错，错误信息={}",j.getMessage());
            throw new RuntimeException("401");
        } catch (NullPointerException e){
            log.error("token获取登录信息出错,token为空，错误信息={}",e.getMessage());
            throw new RuntimeException("404");
        }
    }
}
