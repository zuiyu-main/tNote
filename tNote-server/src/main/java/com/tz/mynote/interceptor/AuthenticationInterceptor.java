package com.tz.mynote.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tz.mynote.annotation.PassToken;
import com.tz.mynote.annotation.UserLoginToken;
import com.tz.mynote.constant.Login;
import com.tz.mynote.constant.RedisKey;
import com.tz.mynote.note.bean.NoteUsers;
import com.tz.mynote.note.service.NoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author tz
 * @Classname AuthenticationInterceptor
 * @Description 登录验证拦截器
 * @Date 2019-09-07 18:08
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private NoteUserService noteUserService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader(Login.AUTHORIZATION);
        // 从 http 请求头中取出 token
        log.info("请求token值读取=[{}]",token);
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    DecodedJWT jwt = JWT.decode(token);
                    userId = jwt.getAudience().get(0);
                    String userName = jwt.getClaim("userName").asString();
                    httpServletRequest.setAttribute("user",userName);
                    log.info("token 解析token = [{}]， userId = [{}],userName = [{}]",JWT.decode(token).toString(),userId,userName);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                Boolean aBoolean = stringRedisTemplate.hasKey(RedisKey.LOGIN_KEY+userId);
                if (!aBoolean){
                    throw new RuntimeException("token 过期，请重新登录");
                }
                NoteUsers user = noteUserService.findUserById(userId);
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

}
