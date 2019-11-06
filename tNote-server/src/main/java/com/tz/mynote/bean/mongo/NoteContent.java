package com.tz.mynote.bean.mongo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tz.mynote.bean.NoteTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

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
    /**
     * @JsonSerialize(using=ToStringSerializer.class)
     * 这个注解可以让Long类型字段转给前端string，防止精度丢失
     */
    @Id
//    @JsonSerialize(using= ToStringSerializer.class)
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 后缀
     */
    private String suffix;
    /**
     * 类型 0 笔记，1 类别
     */
    @Indexed
    private Integer type;
    /**
     * 父id，即所属类别
     */
    @Indexed
    private String itemId;
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
     * 标签集合
     */
    private List<NoteTag> tagList;
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

    /**
     * 前端控制字段
     * 是否 显示inout
     *
     */
    private Boolean inputVisible = false;

}
