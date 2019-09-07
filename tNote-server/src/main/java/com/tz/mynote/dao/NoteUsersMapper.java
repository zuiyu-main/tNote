package com.tz.mynote.dao;

import com.tz.mynote.bean.NoteUsers;
import com.tz.mynote.tk.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NoteUsersMapper extends MyBaseMapper<NoteUsers> {
}