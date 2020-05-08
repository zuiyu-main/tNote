package com.tz.mynote.note.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * @author tz
 */
@Data
@Table(name = "note_organization")
public class NoteOrganization {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 机构名称
     */
    private String title;

    /**
     * 父机构ID
     */
    private Long pid;

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