package com.tz.mynote.service;

import com.tz.mynote.bean.vo.NoteContentVO;
import com.tz.mynote.bean.mongo.NoteContent;
import com.tz.mynote.common.bean.ResultBean;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tz
 * @Classname NoteContentService
 * @Description
 * @Date 2019-09-03 14:40
 */
public interface NoteContentService {
    ResultBean test();

    ResultBean<NoteContent> save(HttpServletRequest request, NoteContentVO noteContentVO);

    ResultBean<NoteContent> delete(HttpServletRequest request, String contentId);

    ResultBean<NoteContent> updateTitle(HttpServletRequest request, String contentId, String title);

    ResultBean<NoteContent> updateContent(HttpServletRequest request, String contentId, String content);

    ResultBean<NoteContent> getItem(HttpServletRequest request);

    ResultBean getNoteByItem(HttpServletRequest request, String itemId);
}
