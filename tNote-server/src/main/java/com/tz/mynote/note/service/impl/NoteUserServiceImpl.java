package com.tz.mynote.note.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.constant.Login;
import com.tz.mynote.constant.RedisKey;
import com.tz.mynote.note.bean.NoteUsers;
import com.tz.mynote.note.bean.vo.NoteUsersVO;
import com.tz.mynote.note.dao.NoteUsersMapper;
import com.tz.mynote.note.service.NoteUserService;
import com.tz.mynote.util.JwtUtil;
import com.tz.mynote.util.LoginInfoUtil;
import com.tz.mynote.util.PasswordUtil;
import com.tz.mynote.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author tz
 * @Classname NoteUserServiceImpl
 * @Description
 * @Date 2019-09-07 18:11
 */
@Slf4j
@Service
public class NoteUserServiceImpl implements NoteUserService {
    @Autowired
    private NoteUsersMapper noteUsersMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private LoginInfoUtil loginInfoUtil;
    private static BeanCopier copier = BeanCopier.create(NoteUsersVO.class,NoteUsers.class,false);
    @Override
    public NoteUsers findUserById(String userId) {
        return noteUsersMapper.selectByPrimaryKey(userId);
    }

    @Override
    public NoteUsers findByUserName(NoteUsers user) {
        NoteUsers users = new NoteUsers();
        users.setUserName(user.getUserName());
        return noteUsersMapper.selectOne(users);
    }

    @Override
    public ResultBean  login(HttpServletRequest request, NoteUsersVO noteUsersVO) {
        log.debug("用户登录，登录信息={}",noteUsersVO.toString());
        NoteUsers user = new NoteUsers();
        copier.copy(noteUsersVO,user,null);
        NoteUsers noteUsers=findByUserName(user);
        if(noteUsers==null){
            return ResultBean.builder().msg("登录失败,用户不存在").code(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value()).build();
        }else {
            boolean b = checkPassWord(noteUsersVO.getPassword() + noteUsers.getSlat(), noteUsers.getPassword());
            if (!b) {
                return ResultBean.builder().msg("登录失败,密码错误").code(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value()).build();
            } else {
                String token = null;
                try {
                    token = JwtUtil.getToken(noteUsers);
                } catch (UnsupportedEncodingException e) {
                    log.error("生成token出错，错误信息=[{}]", e.getMessage());
                    return ResultBean.builder().msg("登录失败,token错误").code(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value()).build();
                }
//                request.setAttribute("Authorization", token);
                JSONObject object = new JSONObject();
                object.put("id", noteUsers.getId());
                object.put("realName", noteUsers.getRealName());
                object.put("orgId", noteUsers.getOrgId());
                object.put("userName", noteUsers.getUserName());
                object.put("passWord", null);
                object.put("createTime", noteUsers.getGmtCreate());
                object.put("wsId",request.getHeader(Login.WEBSOCKET_ID));
                // 踢出其它客户端
                try {
                    checkIsLoginAndKick(RedisKey.LOGIN_KEY+noteUsers.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("用户踢出上一个客户端失败，redisKey={}",RedisKey.LOGIN_KEY+noteUsers.getId());
                }
                stringRedisTemplate.opsForValue().set(RedisKey.LOGIN_KEY+noteUsers.getId(), object.toString(), 7, TimeUnit.DAYS);
                Map<String, Object> map = new HashMap<>(8);
                map.put("token", token);
                map.put("user", object);
                return ResultBean.builder().msg("登录成功").code(HttpStatus.OK.value()).data(map).build();
            }
        }
    }

    /**
     * 校验是否登录
     * @param key
     * @return
     */
    private void checkIsLoginAndKick(String key) throws IOException {
        Boolean aBoolean = stringRedisTemplate.hasKey(key);
        log.info("检测用户是否登录，check redisKey={}",aBoolean);
        if(aBoolean){
            String val = stringRedisTemplate.opsForValue().get(key);
            JSONObject object = JSONObject.parseObject(val, JSONObject.class);
            String wsId = object.getString("wsId");
            log.info("检测用户客户端id={}",wsId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg","logout");
            jsonObject.put("status",404);
            WebSocketServer.sendInfo(jsonObject.toJSONString(),wsId);
        }
    }

    /**
     * 注册
     * 1。 查询是否有账号存在
     * 2。 校验两次输入密码是否一致
     * 3。 加密密码注册
     * @param request
     * @param noteUsersVO
     * @return
     */
    @Override
    public ResultBean register(HttpServletRequest request, NoteUsersVO noteUsersVO) {

        NoteUsers noteUsers = new NoteUsers();
        noteUsers.setUserName(noteUsersVO.getUserName());
        NoteUsers byUserName = noteUsersMapper.selectOne(noteUsers);
        if(null != byUserName){
            log.info("注册用户名失败，用户名重复，输入用户名=[{}]",byUserName.getUserName());
            return ResultBean.builder().code(HttpStatus.RESET_CONTENT.value()).msg("用户名重复,请更换用户名重新注册").build();
        }
        boolean checkTwoPassWord = noteUsersVO.getPassword().equals(noteUsersVO.getPassword2());
        if(!checkTwoPassWord){
            log.info("注册失败，两次密码输入不一致，请验证之后重新输入 ，[{}]-[{}]",noteUsersVO.getPassword(),noteUsersVO.getPassword2());
            return ResultBean.builder().code(HttpStatus.RESET_CONTENT.value()).msg("两次密码输入不一致，请验证之后重新输入").build();
        }
        copier.copy(noteUsersVO,noteUsers,null);
        noteUsers.setSlat(Long.toString(System.currentTimeMillis()));
        String saltPassWord = PasswordUtil.generate(noteUsersVO.getPassword() + noteUsers.getSlat());
        noteUsers.setPassword(saltPassWord);
        noteUsers.setDeleted(new Byte("0"));
        noteUsers.setGmtCreate(new Date());
        noteUsers.setGmtModified(new Date());
        // TODO: 2019-09-22 后续增加机构
        int insert = noteUsersMapper.insert(noteUsers);
        log.info("用户注册结果=[{}],注册信息=[{}]",insert,noteUsers);
        return ResultBean.builder().code(HttpStatus.OK.value()).msg(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Override
    public ResultBean logout(HttpServletRequest request) {
        NoteUsers loginInfo = loginInfoUtil.getLoginInfo(request);
        String key = RedisKey.LOGIN_KEY+loginInfo.getId();
        log.info("系统退出，用户=[{}]",loginInfo);
        Boolean aBoolean = stringRedisTemplate.hasKey(key);
        if(aBoolean){
            stringRedisTemplate.delete(key);
            log.info("redis 移除登录信息，key=[{}]",key);
        }
        return ResultBean.successMsg("退出成功");
    }

    /**
     * 密码校验
     * @param passWord 输入密码
     * @param realPassWord 真实密码
     * @return
     */
    private boolean checkPassWord(String passWord,String realPassWord){
        try {
            boolean verify = PasswordUtil.verify(passWord, realPassWord);
            return verify;
        }catch (Exception e){
            log.error("登录密码校验，密码验证不通过,错误信息=[{}]",e.getMessage());
            return false;
        }
    }
}
