package com.tz.mynote.note.bean.vo;

import com.tz.mynote.common.dao.SaveService;
import com.tz.mynote.common.dao.UpdateService;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
/**
 * @author tz
 */
@Data
public class NoteTagVO {
    @NotNull(message = "id 不能为空",groups = UpdateService.class)
    private Long id;

    /**
     * 机构名称
     */
    @NotNull(message = "标题不能为空",groups = SaveService.class)
    private String title;

    /**
     * 描述
     */
    @NotNull(message = "描述不能为空",groups = SaveService.class)
    private String desc;

    /**
     * 创建者
     */
    private Long create;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 删除标记 0 正常1 删除
     */
    private Byte deleted;
}