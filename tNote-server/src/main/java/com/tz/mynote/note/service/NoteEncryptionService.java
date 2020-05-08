package com.tz.mynote.note.service;

import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.note.bean.vo.NoteEncryptionVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author tz
 * @Classname NoteEncryptionService
 * @Description 加密服务接口
 * @Date 2019-10-10 16:02
 */
public interface NoteEncryptionService {
    /**
     * 加密
     * 前端md5加密之后传到后台
     * 后台加密
     *
     * @param request
     * @param noteEncryptionVO
     * @return
     */
    ResultBean encryption(HttpServletRequest request, NoteEncryptionVO noteEncryptionVO);

    /**
     * 文件夹密码修改
     * @param request
     * @param params
     * @return
     */
    ResultBean update(HttpServletRequest request, Map<String,String> params);

    /**
     * 校验目标文件夹是否加密
     * @param request
     * @param targetId
     * @param password
     * @return
     */
    ResultBean check(HttpServletRequest request, String targetId,String password);
    /**
     * 校验目标文件夹密码是否正确
     * @param request
     * @param targetId
     * @param password
     * @return
     */
    ResultBean verify(HttpServletRequest request, String targetId, String password);
}
