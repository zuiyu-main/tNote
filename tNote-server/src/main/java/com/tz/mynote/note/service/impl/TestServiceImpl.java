package com.tz.mynote.note.service.impl;

import com.tz.mynote.note.bean.TestTa;
import com.tz.mynote.note.dao.TestTaMapper;
import com.tz.mynote.note.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liBai
 * @Classname TestServiceImpl
 * @Description TODO
 * @Date 2019-06-09 09:30
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestTaMapper testTaMapper;
    @Override
    public Object hello() {
        TestTa testTa = testTaMapper.selectByPrimaryKey("1");

        return testTa;
    }
}
