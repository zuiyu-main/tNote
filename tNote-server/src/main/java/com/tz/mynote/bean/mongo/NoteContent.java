package com.tz.mynote.bean.mongo;

import lombok.Data;

/**
 * @author tz
 * @Classname NoteContent
 * @Description mongo对应内容model
 * @Date 2019-09-03 14:33
 */
@Data
public class NoteContent {
    private String id;
    private String title;
    private String content;
    private String create;
    private String gmtCreate;
    private String gmtModified;
    private Integer deleted;

}
