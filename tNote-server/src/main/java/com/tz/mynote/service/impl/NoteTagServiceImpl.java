package com.tz.mynote.service.impl;

import com.tz.mynote.bean.NoteTag;
import com.tz.mynote.bean.NoteUsers;
import com.tz.mynote.bean.vo.NoteTagVO;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.dao.NoteTagMapper;
import com.tz.mynote.service.NoteTagService;
import com.tz.mynote.util.GsonUtil;
import com.tz.mynote.util.LoginInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author tz
 * @Classname NoteTagServiceImpl
 * @Description
 * @Date 2019-11-03 09:00
 */
@Slf4j
@Service
public class NoteTagServiceImpl implements NoteTagService {
    @Autowired
    private NoteTagMapper noteTagMapper;
    @Autowired
    private LoginInfoUtil loginInfoUtil;
    private static BeanCopier beanCopier = BeanCopier.create(NoteTagVO.class,NoteTag.class,false);
    @Override
    public ResultBean save(HttpServletRequest request, NoteTagVO noteTagVO) {
        log.info("保存标签，输入参数=[{}]",noteTagVO.toString());
        NoteUsers loginInfo = loginInfoUtil.getLoginInfo(request);
        NoteTag noteTag = new NoteTag();
        beanCopier.copy(noteTagVO,noteTag,null);
        noteTag.setCreated(loginInfo.getId());
        noteTag.setDeleted(new Byte("0"));
        noteTag.setGmtCreate(new Date());
        noteTag.setGmtModified(new Date());
        log.info("保存标签，保存参数=[{}]",noteTag.toString());
        int insert = noteTagMapper.insert(noteTag);
        log.info("保存标签，保存结果=[{}]", insert);
        return ResultBean.success();
    }

    @Override
    public ResultBean delete(HttpServletRequest request, Long tagId) {
        int i = noteTagMapper.deleteByPrimaryKey(tagId);
        log.info("删除标签，输入参数=[{}],输出结果=[{}]",tagId,i);
        return ResultBean.success();
    }

    @Override
    public ResultBean update(HttpServletRequest request, NoteTagVO noteTagVO) {
        log.info("更新标签内容,输入参数=[{}]",noteTagVO.toString());
        NoteTag noteTag = new NoteTag();
        beanCopier.copy(noteTagVO,noteTag,null);
        int i = noteTagMapper.updateByPrimaryKeySelective(noteTag);
        log.info("更新标签内容，更新数据=[{}],更新结果[{}]",noteTag.toString(),i);
        return ResultBean.success();
    }

    @Override
    public ResultBean get(HttpServletRequest request) {
        NoteUsers loginInfo = loginInfoUtil.getLoginInfo(request);
        log.info("获取我所有的标签,查询条件=[{}]",loginInfo.toString());
        NoteTag noteTag = new NoteTag();
        noteTag.setCreated(loginInfo.getId());
        List<NoteTag> select = noteTagMapper.select(noteTag);
        log.info("获取我的标签结束,返回结果=[{}]", GsonUtil.toJson(select));
        return ResultBean.successData(select);
    }
}
