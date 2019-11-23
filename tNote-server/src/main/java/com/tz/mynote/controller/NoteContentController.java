package com.tz.mynote.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tz.mynote.annotation.OptionalLog;
import com.tz.mynote.annotation.UserLoginToken;
import com.tz.mynote.bean.vo.NoteContentVO;
import com.tz.mynote.bean.mongo.NoteContent;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.common.dao.SaveService;
import com.tz.mynote.service.NoteContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author tz
 * @Classname NoteContentController
 * @Description
 * @Date 2019-09-03 14:39
 */
@Api(value = "NoteContentController",tags = "日记内容区")
@RestController
@RequestMapping("/note")
public class NoteContentController {
    @Autowired
    private NoteContentService noteContentService;
    @OptionalLog(module="日记内容", methods="test测试接口")
    @GetMapping("/test")
//    @UserLoginToken
    public ResultBean test(){
        return noteContentService.test();
    }

    @PostMapping("/save")
    @UserLoginToken
    @OptionalLog(module="日记内容", methods="保存分类或日记内容")
    @ApiOperation(value ="save",notes = "保存分类或者日记内容",tags = "日记内容区")
    public ResultBean save(HttpServletRequest request, @RequestBody @Validated(value = SaveService.class) NoteContentVO noteContentVO, BindingResult bindingResult){
        return noteContentService.save(request,noteContentVO);
    }
    @DeleteMapping("/delete")
    @UserLoginToken
    @OptionalLog(module="日记内容", methods="删除分类或笔记")
    @ApiOperation(value ="delete",notes = "删除分类或者日记内容",tags = "日记内容区")
    public ResultBean delete(HttpServletRequest request,@RequestBody Map<String,Object> params){
        return noteContentService.delete(request,params.get("contentId").toString());
    }
    @PutMapping("/updateTitle")
    @UserLoginToken
    @OptionalLog(module="日记内容", methods="修改名称")
    @ApiOperation(value ="updateTitle",notes = "修改名称",tags = "日记内容区")
    public ResultBean updateTitle(HttpServletRequest request,@RequestParam String contentId,@RequestParam String title ){
        return noteContentService.updateTitle(request,contentId,title);
    }
    @PutMapping("/updateContent")
    @UserLoginToken
    @OptionalLog(module="日记内容", methods="修改内容")
    @ApiOperation(value ="updateContent",notes = "修改内容",tags = "日记内容区")
    public ResultBean updateContent(HttpServletRequest request,@RequestParam String contentId,@RequestParam String content ){
        return noteContentService.updateContent(request,contentId,content);
    }
    @GetMapping("/getItem")
    @UserLoginToken
    @OptionalLog(module="日记内容", methods="获取我的分类")
    @ApiOperation(value ="getItem",notes = "获取我的分类",tags = "日记内容区")
    public ResultBean getItem(HttpServletRequest request){
        return noteContentService.getItem(request);
    }
    @GetMapping("/getNoteByItem")
    @UserLoginToken
    @OptionalLog(module="日记内容", methods="获取某个分类下面笔记")
    @ApiOperation(value ="getNoteByItem",notes = "获取某个分类下面笔记",tags = "日记内容区")
    public ResultBean getNoteByItem(HttpServletRequest request,@RequestParam String itemId){
        return noteContentService.getNoteByItem(request,itemId);
    }

    @PutMapping("/updateNoteTag")
    @UserLoginToken
    @OptionalLog(module="日记内容", methods="修改笔记标签")
    @ApiOperation(value ="updateNoteTag",notes = "修改笔记标签",tags = "日记内容区")
    public ResultBean updateNoteTag(HttpServletRequest request,@RequestBody Map<String,Object> params){
        return noteContentService.updateNoteTag(request,params.get("contentId").toString(), JSONArray.toJSONString(params.get("tagList")));
    }



}
