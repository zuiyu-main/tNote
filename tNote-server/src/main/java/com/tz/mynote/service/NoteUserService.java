package com.tz.mynote.service;

import com.tz.mynote.bean.NoteUsers;
import com.tz.mynote.bean.VO.NoteUsersVO;
import com.tz.mynote.common.bean.ResultBean;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tz
 * @Classname NoteUserService
 * @Description
 * @Date 2019-09-07 18:11
 */
public interface NoteUserService {
    NoteUsers findUserById(String userId);

    NoteUsers findByUserName(NoteUsers user);

    ResultBean login(HttpServletRequest request, NoteUsersVO noteUsersVO);

    ResultBean register(HttpServletRequest request, NoteUsersVO noteUsersVO);
}
