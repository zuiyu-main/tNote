package com.tz.mynote.service.impl;

import com.tz.mynote.bean.NoteLog;
import com.tz.mynote.dao.NoteLogMapper;
import com.tz.mynote.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tz
 * @Classname LogServiceImpl
 * @Description
 * @Date 2019-09-06 09:25
 */
@Service
@Slf4j
public class LogServiceImpl implements LogService {
    @Autowired
    private NoteLogMapper noteLogMapper;
    @Override
    public void save(NoteLog noteLog) {
        log.info("日志保存[{}]",noteLog.toString());
        noteLogMapper.insertSelective(noteLog);
    }
}
