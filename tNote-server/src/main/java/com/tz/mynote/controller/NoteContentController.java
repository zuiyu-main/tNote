package com.tz.mynote.controller;

import com.tz.mynote.annotation.OptionalLog;
import com.tz.mynote.annotation.UserLoginToken;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.service.NoteContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tz
 * @Classname NoteContentController
 * @Description
 * @Date 2019-09-03 14:39
 */
@RestController
@RequestMapping("/note")
public class NoteContentController {
    @Autowired
    private NoteContentService noteContentService;
    @OptionalLog(module="日记内容", methods="test测试接口")
    @GetMapping("/test")
    @UserLoginToken
    public ResultBean test(){
        return noteContentService.test();
    }

}
