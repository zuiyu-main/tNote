package com.tz.mynote.bean;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "test_ta")
public class TestTa {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_modify_time")
    private Date lastModifyTime;

    @Column(name = "text_blog")
    private String textBlog;
}