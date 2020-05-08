package com.tz.mynote.note.service;

import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.note.bean.vo.NoteTagVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tz
 * @Classname NoteTagService
 * @Description
 * @Date 2019-11-03 09:00
 */
public interface NoteTagService {
    ResultBean save(HttpServletRequest request, NoteTagVO noteTagVO);

    ResultBean delete(HttpServletRequest request, Long tagId);

    ResultBean update(HttpServletRequest request, NoteTagVO noteTagVO);

    ResultBean get(HttpServletRequest request);

}
