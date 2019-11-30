package com.tz.mynote.note.dao;

import com.tz.mynote.note.bean.NoteTag;
import com.tz.mynote.tk.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author cxt
 */
@Mapper
@Repository
public interface NoteTagMapper extends MyBaseMapper<NoteTag> {
}