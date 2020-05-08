package com.tz.mynote.note.service.impl;

import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.note.bean.NoteEncryption;
import com.tz.mynote.note.bean.NoteUsers;
import com.tz.mynote.note.bean.vo.NoteEncryptionVO;
import com.tz.mynote.note.dao.NoteEncryptionMapper;
import com.tz.mynote.note.service.NoteEncryptionService;
import com.tz.mynote.util.LoginInfoUtil;
import com.tz.mynote.util.PasswordUtil;
import com.tz.mynote.util.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tz
 * @Classname NoteEncryptionServiceImpl
 * @Description 加密服务实现
 * @Date 2019-10-10 16:02
 */
@Slf4j
@Service
public class NoteEncryptionServiceImpl implements NoteEncryptionService {
    private static SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(2,1);
    @Autowired
    private NoteEncryptionMapper noteEncryptionMapper;
    @Autowired
    private LoginInfoUtil loginInfoUtil;

    /**
     * 加密
     * 前端md5加密之后传到后台
     * 后台加密
     *
     * @param request
     * @param noteEncryptionVO
     * @return
     */
    @Override
    public ResultBean encryption(HttpServletRequest request, NoteEncryptionVO noteEncryptionVO) {
        log.debug("加密开始，接受参数=【{}】",noteEncryptionVO.toString());
        NoteEncryption select = new NoteEncryption();
        select.setDeleted(new Byte("0"));
        select.setTargetId(noteEncryptionVO.getTargetId());
        List<NoteEncryption> selectRes = noteEncryptionMapper.select(select);
        if(!CollectionUtils.isEmpty(selectRes)){
            return ResultBean.builder().msg("类目已经加密，加密失败，请进行修改密码").code(HttpStatus.BAD_REQUEST.value()).build();
        }
        String encryptionPassword = PasswordUtil.generate(noteEncryptionVO.getPassword());
        NoteEncryption noteEncryption = new NoteEncryption();
//        noteEncryption.setId(snowFlakeUtil.nextId());
        noteEncryption.setDeleted(new Byte("0"));
        noteEncryption.setGmtCreate(new Date());
        noteEncryption.setGmtModified(new Date());
        noteEncryption.setTargetId(noteEncryptionVO.getTargetId());
        noteEncryption.setPassword(encryptionPassword);
        try {
            int insert = noteEncryptionMapper.insertSelective(noteEncryption);
            log.info("加密结果返回=【{}】",insert);

        }catch (Exception e){
            log.error("文件夹加密失败，错误信息=[{}]", e.getMessage());
            return ResultBean.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).msg("加密失败，请联系管理员").data(e.getMessage()).build();
        }
        return ResultBean.success();
    }

    @Override
    public ResultBean update(HttpServletRequest request,Map<String,String> params){
        String newPass = params.get("newPass");
        String lPass = params.get("lPass");
        String targetId = params.get("targetId");
        if(StringUtil.isEmpty(newPass)){
            return ResultBean.builder().code(HttpStatus.BAD_REQUEST.value()).msg("the parameter newPass is must not be null").build();
        }
        if(StringUtil.isEmpty(lPass)){
            return ResultBean.builder().code(HttpStatus.BAD_REQUEST.value()).msg("the parameter lPass is must not be null").build();
        }
        if(StringUtil.isEmpty(targetId)){
            return ResultBean.builder().code(HttpStatus.BAD_REQUEST.value()).msg("the parameter targetId is must not be null").build();
        }
        log.debug("文件夹更新密码，传入参数1=[{}],2=[{}],3=[{}]",newPass,lPass,targetId);
        NoteUsers loginInfo = loginInfoUtil.getLoginInfo(request);
        boolean verify = PasswordUtil.verify(lPass+loginInfo.getSlat(), loginInfo.getPassword());
        if(verify){
            NoteEncryption noteEncryption = new NoteEncryption();
            noteEncryption.setTargetId(targetId);
            List<NoteEncryption> select = noteEncryptionMapper.select(noteEncryption);
            select.get(0).setPassword(PasswordUtil.generate(newPass));
            try {
                int i = noteEncryptionMapper.updateByPrimaryKeySelective(select.get(0));
                log.debug("更新密码返回，更新结果=[{}],更新前=[{}]",i,lPass);
            }catch (Exception e){
                log.error("密码修改报错，报错信息={}",e.getMessage());
                throw e;
            }
            return ResultBean.success();
        }else{
            return ResultBean.builder().code(HttpStatus.BAD_REQUEST.value()).msg("登录密码校验未通过，请更改密码后进行重试").build();
        }
    }

    @Override
    public ResultBean check(HttpServletRequest request, String targetId,String password) {
        NoteEncryption select  = new NoteEncryption();
        select.setTargetId(targetId);
        List<NoteEncryption> res = noteEncryptionMapper.select(select);

        if(CollectionUtils.isEmpty(res)){
            return ResultBean.builder().msg(HttpStatus.OK.getReasonPhrase()).code(HttpStatus.OK.value()).data(false).build();
        }else {
            return ResultBean.builder().msg(HttpStatus.OK.getReasonPhrase()).code(HttpStatus.OK.value()).data(true).build();
        }
    }

    @Override
    public ResultBean verify(HttpServletRequest request, String targetId, String password) {
        if(StringUtils.isEmpty(targetId)){
            return ResultBean.success("targetId must not be null",null);
        }
        if(StringUtils.isEmpty(password)){
            return ResultBean.success("password must not be null",null);
        }
        NoteEncryption select  = new NoteEncryption();
        select.setTargetId(targetId);
        List<NoteEncryption> res = noteEncryptionMapper.select(select);
        if(CollectionUtils.isEmpty(res)){
            return ResultBean.builder().msg("暂未查询到加密内容").code(HttpStatus.OK.value()).data(false).build();
        }
        NoteEncryption noteEncryption = res.get(0);
        log.info("前端加密字符串{}，后端再次加密{}，检验结果{}",password,noteEncryption.getPassword(),PasswordUtil.verify(password,noteEncryption.getPassword()));
        boolean verify = PasswordUtil.verify(password, noteEncryption.getPassword());
        if (verify) {
            return ResultBean.builder().msg(HttpStatus.OK.getReasonPhrase()).code(HttpStatus.OK.value()).data(true).build();
        }else {
            return ResultBean.builder().msg("密码不正确，请重新输入密码").code(HttpStatus.OK.value()).data(false).build();
        }
    }

    /**
     * 文件夹密码修改参数校验
     * @param params
     */
    private void checkUpdateParameter(Map<String, String> params) {
        String newPass = params.get("newPass");
        String lPass = params.get("lPass");
        String targetId = params.get("targetId");

        Assert.notNull(newPass,"the parameter newPass is must not be null");
        Assert.notNull(lPass,"the parameter lPass is must not be null");
        Assert.notNull(targetId,"the parameter targetId is must not be null");
    }

    public static void main(String[] args) {
        System.out.println(PasswordUtil.generate("202cb962ac59075b964b07152d234b70"));
    }
}
