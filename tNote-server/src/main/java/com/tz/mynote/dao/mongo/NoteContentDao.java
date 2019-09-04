package com.tz.mynote.dao.mongo;

import com.tz.mynote.bean.mongo.NoteContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author tz
 * @Classname NoteContentDao
 * @Description 操作文件内容
 * @Date 2019-09-03 14:36
 */
public interface NoteContentDao extends MongoRepository<NoteContent,String> {
}
