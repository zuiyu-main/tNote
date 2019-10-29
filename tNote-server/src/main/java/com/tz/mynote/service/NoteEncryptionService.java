package com.tz.mynote.service;

import com.tz.mynote.bean.vo.NoteEncryptionVO;
import com.tz.mynote.common.bean.ResultBean;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author tz
 * @Classname NoteEncryptionService
 * @Description 加密服务接口
 * @Date 2019-10-10 16:02
 */
public interface NoteEncryptionService {
    ResultBean encryption(HttpServletRequest request, NoteEncryptionVO noteEncryptionVO);

    ResultBean update(HttpServletRequest request, Map<String,String> params);

    ResultBean check(HttpServletRequest request, String targetId,String password);

    ResultBean verify(HttpServletRequest request, String targetId, String password);
}
