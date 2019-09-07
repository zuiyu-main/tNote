package com.tz.mynote.service;

import com.tz.mynote.bean.NoteUsers;

/**
 * @author tz
 * @Classname NoteUserService
 * @Description
 * @Date 2019-09-07 18:11
 */
public interface NoteUserService {
    NoteUsers findUserById(String userId);

    NoteUsers findByUserName(NoteUsers user);

}
