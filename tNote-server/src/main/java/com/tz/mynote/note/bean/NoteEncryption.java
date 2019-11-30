package com.tz.mynote.note.bean;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "note_encryption")
public class NoteEncryption {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 加密对象id
     */
    @Column(name = "target_id")
    private String targetId;

    /**
     * 密码
     */
    private String password;

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
     * 删除标记0 false，1true
     */
    private Byte deleted;
}