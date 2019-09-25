package com.tz.mynote.bean.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author tz
 * @Classname NoteContent
 * @Description mongo对应内容model
 * @url https://blog.csdn.net/tianyaleixiaowu/article/details/73530679
 * @Date 2019-09-03 14:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="NoteContent")
public class NoteContent {
    @Id
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 类型 0 笔记，1 类别
     */
    @Indexed
    private Integer type;
    /**
     * 父id，即所属类别
     */
    @Indexed
    private Long itemId;
    /**
     * 创建人
     */
    private String author;
    /**
     * 创建人id
     */
    @Indexed
    private Long authorId;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 删除标记 0 false默认。1删除
     */
    private Integer deleted;

}
