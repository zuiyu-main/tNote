package com.tz.mynote.service.impl;

import com.tz.mynote.bean.mongo.NoteContent;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.dao.mongo.NoteContentDao;
import com.tz.mynote.service.NoteContentService;
import com.tz.mynote.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author tz
 * @Classname NoteContentServiceImpl
 * @Description
 * @Date 2019-09-03 14:40
 */
@Service("noteContentService")
@Slf4j
public class NoteContentServiceImpl implements NoteContentService {
    @Autowired
    private NoteContentDao noteContentDao;

    @Override
    public ResultBean test() {
        NoteContent content = new NoteContent();
        content.setId(UUID.randomUUID().toString());
        content.setTitle("test");
        content.setContent("测试mongodb联通");
        content.setCreate("1");
        content.setDeleted(0);
        content.setGmtCreate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        content.setGmtModified(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        NoteContent insert = noteContentDao.insert(content);
        log.info("插入数据显示[{}]",insert.toString());
        return ResultBean.builder().code(HttpStatus.OK.value()).data(insert).msg(HttpStatus.OK.getReasonPhrase()).build();
    }
}
