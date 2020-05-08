package com.tz.mynote.note.service;

import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.note.bean.vo.NoteContentVO;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

/**
 * @author tz
 * @Classname NoteContentService
 * @Description
 * @Date 2019-09-03 14:40
 */
public interface NoteContentService {
    ResultBean test();

    /**
     * 保存
     * @param request
     * @param noteContentVO
     * @return
     */
    ResultBean save(HttpServletRequest request, NoteContentVO noteContentVO) throws FileNotFoundException, IllegalAccessException;

    /**
     * 删除
     * @param request
     * @param contentId
     * @return
     */
    ResultBean delete(HttpServletRequest request, String contentId);

    /**
     * 修改标题
     * @param request
     * @param contentId
     * @param title
     * @return
     */
    ResultBean updateTitle(HttpServletRequest request, String contentId, String title);

    /**
     * 更新文件内容
     * @param request
     * @param contentId
     * @param content
     * @return
     */
    ResultBean updateContent(HttpServletRequest request, String contentId, String content);

    /**
     * 获取我的文件夹
     * @param request
     * @return
     */
    ResultBean getItem(HttpServletRequest request);

    /**
     * 根据文件夹查询日记
     * @param request
     * @param itemId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultBean getNoteByItem(HttpServletRequest request, String itemId,Integer pageNum,Integer pageSize);

    /**
     * 更新日记tag
     * @param request
     * @param contentId
     * @param tagList
     * @return
     */
    ResultBean updateNoteTag(HttpServletRequest request, String contentId, String tagList);
}
