package com.tz.mynote.service.impl;


import com.tz.mynote.bean.NoteUsers;
import com.tz.mynote.dao.NoteUsersMapper;
import com.tz.mynote.service.NoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tz
 * @Classname NoteUserServiceImpl
 * @Description
 * @Date 2019-09-07 18:11
 */
@Slf4j
@Service
public class NoteUserServiceImpl implements NoteUserService {
    @Autowired
    private NoteUsersMapper noteUsersMapper;
    @Override
    public NoteUsers findUserById(String userId) {
        return noteUsersMapper.selectByPrimaryKey(userId);
    }

    @Override
    public NoteUsers findByUserName(NoteUsers user) {
        NoteUsers users = new NoteUsers();
        user.setUserName(user.getUserName());
        return noteUsersMapper.selectOne(users);
    }
}
