package com.tz.mynote.note.bean;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Table(name = "test_ta")
public class TestTa {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    @DateTimeFormat(pattern ="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;
    @DateTimeFormat(pattern ="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "last_modify_time")
    private Date lastModifyTime;
    @Column(name = "text_blog")
    private String textBlog;
}