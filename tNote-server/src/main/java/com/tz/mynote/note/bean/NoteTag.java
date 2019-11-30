package com.tz.mynote.note.bean;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "note_tag")
public class NoteTag {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 机构名称
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建者
     */
    private Long created;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 删除标记 0 正常1 删除
     */
    private Byte deleted;


}