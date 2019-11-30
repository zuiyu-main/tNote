package com.tz.mynote.note.bean.vo;

import com.tz.mynote.common.dao.SaveService;
import com.tz.mynote.common.dao.UpdateService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author tz
 * @Classname NoteContent
 * @Description mongo对应内容model
 * @Date 2019-09-03 14:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteContentVO {
    @NotNull(message = "id must not be null",groups = UpdateService.class)
    private String id;
    /**
     * 标题
     */
    @NotNull(message = "title must not be null",groups = SaveService.class)
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
    @NotNull(message = "type must not be null",groups = SaveService.class)
    private Integer type;
    /**
     * 父id，即所属类别
     */
    private String itemId;
    /**
     * 创建人
     */
    private String author;
    /**
     * 创建人id
     */
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
