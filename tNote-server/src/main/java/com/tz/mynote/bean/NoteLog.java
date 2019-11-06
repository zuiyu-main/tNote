package com.tz.mynote.bean;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "note_log")
public class NoteLog {
    /**
     * 操作日志id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 操作人
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 执行模块
     */
    private String module;

    /**
     * 执行方法
     */
    private String methods;

    /**
     * 操作内容
     */
    private String content;

    /**
     * 请求路径
     */
    @Column(name = "action_url")
    private String actionUrl;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 操作时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 执行描述（1:执行成功、2:执行失败）
     */
    private Byte commit;

    /**
     * 请求地址
     */
    private String actionAddress;
}