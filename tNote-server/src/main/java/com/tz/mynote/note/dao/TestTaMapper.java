package com.tz.mynote.note.dao;

import com.tz.mynote.note.bean.TestTa;
import com.tz.mynote.tk.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
@Mapper
@Repository
public interface TestTaMapper extends MyBaseMapper<TestTa> {
}