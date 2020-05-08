package com.tz.mynote.note.service;

import com.tz.mynote.note.bean.NoteLog;

/**
 * @author tz
 * @Classname LogService
 * @Description
 * @Date 2019-09-06 09:25
 */
public interface LogService {
    /**
     * 保存操作日志
     * @param noteLog
     */
    void save(NoteLog noteLog);
}
