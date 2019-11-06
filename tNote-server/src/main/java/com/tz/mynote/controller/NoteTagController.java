package com.tz.mynote.controller;

import com.tz.mynote.annotation.OptionalLog;
import com.tz.mynote.annotation.UserLoginToken;
import com.tz.mynote.bean.NoteTag;
import com.tz.mynote.bean.mongo.NoteContent;
import com.tz.mynote.bean.vo.NoteContentVO;
import com.tz.mynote.bean.vo.NoteTagVO;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.common.dao.SaveService;
import com.tz.mynote.common.dao.UpdateService;
import com.tz.mynote.service.NoteTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tz
 * @Classname NoteTagController
 * @Description 标签管理
 * @Date 2019-11-03 08:59
 */
@Api(value = "NoteTagController",tags = "标签管理")
@RestController
@RequestMapping("/tag")
public class NoteTagController {
    @Autowired
    private NoteTagService noteTagService;
    @PostMapping("/save")
    @UserLoginToken
    @OptionalLog(module="标签管理", methods="保存标签")
    @ApiOperation(value ="save",notes = "保存标签",tags = "标签管理")
    public ResultBean save(HttpServletRequest request, @RequestBody @Validated(value = SaveService.class) NoteTagVO noteTagVO, BindingResult bindingResult){
        return noteTagService.save(request,noteTagVO);
    }
    @DeleteMapping("/delete")
    @UserLoginToken
    @OptionalLog(module="标签管理", methods="根据id删除标签")
    @ApiOperation(value ="delete",notes = "删除标签",tags = "标签管理")
    public ResultBean delete(HttpServletRequest request, @RequestParam Long tagId){
        return noteTagService.delete(request,tagId);
    }
    @PostMapping("/update")
    @UserLoginToken
    @OptionalLog(module="标签管理", methods="修改标签")
    @ApiOperation(value ="update",notes = "修改标签",tags = "标签管理")
    public ResultBean update(HttpServletRequest request, @RequestBody @Validated(value = UpdateService.class) NoteTagVO noteTagVO, BindingResult bindingResult){
        return noteTagService.update(request,noteTagVO);
    }
    @GetMapping("/get")
    @UserLoginToken
    @OptionalLog(module="标签管理", methods="查看我的标签")
    @ApiOperation(value ="get",notes = "查看我的标签",tags = "标签管理")
    public ResultBean get(HttpServletRequest request){
        return noteTagService.get(request);
    }
}
