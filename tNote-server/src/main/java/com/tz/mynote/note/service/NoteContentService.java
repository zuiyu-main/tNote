package com.tz.mynote.note.service;

import com.tz.mynote.note.bean.vo.NoteContentVO;
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

    ResultBean save(HttpServletRequest request, NoteContentVO noteContentVO);

    ResultBean delete(HttpServletRequest request, String contentId);

    ResultBean updateTitle(HttpServletRequest request, String contentId, String title);

    ResultBean updateContent(HttpServletRequest request, String contentId, String content);

    ResultBean getItem(HttpServletRequest request);

    ResultBean getNoteByItem(HttpServletRequest request, String itemId);

    ResultBean updateNoteTag(HttpServletRequest request, String contentId, String tagList);
}
