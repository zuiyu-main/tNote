package com.tz.mynote.note.controller;

import com.tz.mynote.annotation.OptionalLog;
import com.tz.mynote.annotation.UserLoginToken;
import com.tz.mynote.note.bean.vo.NoteEncryptionVO;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.common.dao.SaveService;
import com.tz.mynote.note.service.NoteEncryptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    @OptionalLog(module="日记加密", methods="重置密码")
    @ApiOperation(value ="update",notes = "重置密码",tags = "日记加密区")
    public ResultBean update(HttpServletRequest request, @RequestBody Map<String,String> params){
        return noteEncryptionService.update(request,params);
    }
    @GetMapping("/check")
    @UserLoginToken
    @OptionalLog(module = "日记加密",methods = "检查是否已经加密")
    @ApiOperation(value ="check",notes = "查看是否加密",tags = "日记加密区")
    public ResultBean check(HttpServletRequest request, @RequestParam String targetId,String password){
        return noteEncryptionService.check(request,targetId,password);
    }
    @PostMapping("/verify")
    @UserLoginToken
    @OptionalLog(module = "日记加密",methods = "校验加密密码")
    @ApiOperation(value ="verify",notes = "检查加密",tags = "日记加密区")
    public ResultBean verify(HttpServletRequest request, @RequestBody Map<String,String> params){
        return noteEncryptionService.verify(request,params.get("targetId"),params.get("password"));
    }


}
