package com.tz.mynote.controller;

import com.tz.mynote.annotation.OptionalLog;
import com.tz.mynote.annotation.UserLoginToken;
import com.tz.mynote.bean.vo.NoteEncryptionVO;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.common.dao.SaveService;
import com.tz.mynote.common.dao.UpdateService;
import com.tz.mynote.service.NoteEncryptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tz
 * @Classname NoteEncryptionController
 * @Description 加密控制中心
 * @Date 2019-10-10 15:59
 */
@Api(value = "NoteEncryptionController",tags = "日记加密区")
@RestController
@RequestMapping("/encryption")
public class NoteEncryptionController {
    @Autowired
    private NoteEncryptionService noteEncryptionService;
    @PostMapping("/item")
    @UserLoginToken
    @OptionalLog(module="日记加密", methods="加密文件夹")
    @ApiOperation(value ="encryption",notes = "加密文件夹",tags = "日记加密区")
    public ResultBean encryption(HttpServletRequest request, @RequestBody @Validated(value = SaveService.class) NoteEncryptionVO noteEncryptionVO){
        return noteEncryptionService.encryption(request,noteEncryptionVO);
    }
    @PostMapping("/update")
    @UserLoginToken
    @OptionalLog(module="日记加密", methods="修改密码")
    @ApiOperation(value ="update",notes = "修改密码",tags = "日记加密区")
    public ResultBean update(HttpServletRequest request, @RequestBody @Validated(value = UpdateService.class) NoteEncryptionVO noteEncryptionVO){
        return noteEncryptionService.update(request,noteEncryptionVO);
    }
}
