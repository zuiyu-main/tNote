package com.tz.mynote.service.impl;

import com.tz.mynote.bean.NoteEncryption;
import com.tz.mynote.bean.VO.NoteEncryptionVO;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.dao.NoteEncryptionMapper;
import com.tz.mynote.service.NoteEncryptionService;
import com.tz.mynote.util.PasswordUtil;
import com.tz.mynote.util.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    @Override
    public ResultBean encryption(HttpServletRequest request, NoteEncryptionVO noteEncryptionVO) {
        log.debug("加密开始，接受参数=【{}】",noteEncryptionVO.toString());
        String encryptionPassword = PasswordUtil.generate(noteEncryptionVO.getPassword());
        NoteEncryption noteEncryption = new NoteEncryption();
        noteEncryption.setId(snowFlakeUtil.nextId());
        noteEncryption.setDeleted(new Byte("0"));
        noteEncryption.setGmtCreate(new Date());
        noteEncryption.setGmtModified(new Date());
        noteEncryption.setTargetId(noteEncryption.getTargetId());
        noteEncryption.setPassword(encryptionPassword);
        int insert = noteEncryptionMapper.insert(noteEncryption);
        log.info("加密结果返回=【{}】",insert);
        return ResultBean.success();
    }

    @Override
    public ResultBean update(HttpServletRequest request, NoteEncryptionVO noteEncryptionVO) {
        NoteEncryption noteEncryption = noteEncryptionMapper.selectByPrimaryKey(noteEncryptionVO.getId());
        noteEncryption.setPassword(PasswordUtil.generate(noteEncryptionVO.getPassword()));
        int i = noteEncryptionMapper.insertSelective(noteEncryption);
        log.debug("更新密码返回，更新结果=【{}】,更新前=【{}】，更新后=【{}】,",i,noteEncryptionVO.getPassword(),noteEncryption.getPassword());
        return ResultBean.success();
    }
}
