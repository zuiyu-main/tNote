package com.tz.mynote.note.dao;

import com.tz.mynote.note.bean.NoteEncryption;
import com.tz.mynote.tk.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
/**
 * @author tz
 */
@Mapper
@Repository
public interface NoteEncryptionMapper extends MyBaseMapper<NoteEncryption> {
}