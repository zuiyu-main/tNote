package com.tz.mynote.bean;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "note_users")
public class NoteUsers {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 登录名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 加密之后的密码
     */
    private String password;

    /**
     * 用户真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 密码加密的盐
     */
    private String slat;

    /**
     * 机构id
     */
    @Column(name = "org_id")
    private String orgId;

    /**
     * 创建日期
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改日期
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 删除标记（1，删除，0 正常）默认0
     */
    private Byte deleted;
}