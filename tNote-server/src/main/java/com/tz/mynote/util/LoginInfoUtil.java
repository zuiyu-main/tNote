package com.tz.mynote.util;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tz.mynote.constant.Login;
import com.tz.mynote.note.bean.NoteUsers;
import com.tz.mynote.note.service.NoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

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

    /**
     * 获取请求ip
     * @param request
     * @return
     */
    public static String getRequestIp(HttpServletRequest request){
        // 获取访问真实IP
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress)|| "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 根据ip获取地址
     * @param ip
     * @return
     */
    public static String getAddress(String ip){
        String resout = "";
        try{
            String str = getJsonContent("http://ip.taobao.com/service/getIpInfo.php?ip="+ip);
            System.out.println(str);
            JSONObject obj = JSONObject.parseObject(str);
            JSONObject obj2 =  (JSONObject) obj.get("data");
            Integer code = obj.getInteger("code");
            if(code==0){
                resout =  obj2.get("country")+"--" +obj2.get("area")+"--" +obj2.get("city")+"--" +obj2.get("isp");
            }else{
                resout =  "IP地址有误";
            }
        }catch(Exception e){

            e.printStackTrace();
            resout = "获取IP地址异常："+e.getMessage();
        }
        return resout;
    }
    public static String getJsonContent(String urlStr) {
        try
        {// 获取HttpURLConnection连接对象
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(3000);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
            // 获取相应码
            int respCode = httpConn.getResponseCode();
            if (respCode == 200)
            {
                return convertStream2Json(httpConn.getInputStream());
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    private static String convertStream2Json(InputStream inputStream) {
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try
        {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
            {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonStr;
    }
}
