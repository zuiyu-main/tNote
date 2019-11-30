package com.tz.mynote.note.bean.vo;

import com.tz.mynote.common.dao.SaveService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author cxt
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteUsersVO {
    private Long id;

    /**
     * 登录名
     */
    @NotNull(message = "用户名不能为空",groups = SaveService.class)
    private String userName;

    /**
     * 加密之后的密码
     */
    @NotNull(message = "用户密码不能为空",groups = SaveService.class)
    private String password;

    @NotNull(message = "用户校验密码不能为空",groups = SaveService.class)
    private String password2;

    /**
     * 用户真实姓名
     */
    @NotNull(message = "用户真实姓名不能为空",groups = SaveService.class)
    private String realName;

    /**
     * 密码加密的盐
     */
    private String slat;

    /**
     * 机构id
     */
    private String orgId;

    /**
     * 创建日期
     */
    private Date gmtCreate;

    /**
     * 修改日期
     */
    private Date gmtModified;

    /**
     * 删除标记（1，删除，0 正常）默认0
     */
    private Byte deleted;

}