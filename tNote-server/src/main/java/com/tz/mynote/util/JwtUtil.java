package com.tz.mynote.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tz.mynote.note.bean.NoteUsers;

import java.io.UnsupportedEncodingException;

/**
 * @author tz
 * @Classname JwtUtil
 * @Description https://www.cnblogs.com/shihaiming/p/9565835.html
 * @Date 2019-09-07 18:23
 */
public class JwtUtil {
    public static String getToken(NoteUsers user) throws UnsupportedEncodingException {
        String token="";
        token= JWT.create().withAudience(user.getId().toString())
                .withClaim("userName",user.getUserName())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
