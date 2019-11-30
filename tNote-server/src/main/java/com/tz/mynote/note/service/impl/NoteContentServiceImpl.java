package com.tz.mynote.note.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.mongodb.client.result.UpdateResult;
import com.tz.mynote.note.bean.NoteTag;
import com.tz.mynote.note.bean.NoteUsers;
import com.tz.mynote.note.bean.vo.NoteContentVO;
import com.tz.mynote.note.bean.mongo.NoteContent;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.constant.CommonConstant;
import com.tz.mynote.constant.FileSuffixEnum;
import com.tz.mynote.constant.MongoCollectionName;
import com.tz.mynote.note.dao.NoteTagMapper;
import com.tz.mynote.note.dao.mongo.NoteContentDao;
import com.tz.mynote.note.service.NoteContentService;
import com.tz.mynote.util.LoginInfoUtil;
import com.tz.mynote.util.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author tz
 * @Classname NoteContentServiceImpl
 * @Description
 * @Date 2019-09-03 14:40
 */
@Service
@Slf4j
public class NoteContentServiceImpl implements NoteContentService {
    @Autowired
    private NoteContentDao noteContentDao;
    @Autowired
    private LoginInfoUtil loginInfoUtil;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private NoteTagMapper noteTagMapper;
    private static BeanCopier beanCopier = BeanCopier.create(NoteContentVO.class,NoteContent.class,false);
    private static SnowFlakeUtil SNOWFLAKEUTIL = new SnowFlakeUtil(1, 9);

    @Override
    public ResultBean test() {
        NoteContent content = new NoteContent();
        content.setId(UUID.randomUUID().toString());
        content.setTitle("test");
        content.setContent("测试mongodb联通");
        content.setAuthor("田子");
        content.setDeleted(0);
        content.setGmtCreate(new Date());
        content.setGmtModified(new Date());
        NoteContent insert = noteContentDao.insert(content);
        log.info("插入数据显示[{}]",insert.toString());
        return ResultBean.builder().code(HttpStatus.OK.value()).data(insert).msg(HttpStatus.OK.getReasonPhrase()).build();
    }

    /**
     * 保存分类或者文件
     * 判断文件类型，如果是文件判断itemId，不是统一设置未分类的baseItemId
     * @param request
     * @param noteContentVO
     * @return
     */
    @Override
    public ResultBean save(HttpServletRequest request, NoteContentVO noteContentVO) {
        log.info("保存（分类）日记，接受参数=【{}】",noteContentVO);
        NoteContent content = new NoteContent();
        NoteUsers loginInfo = loginInfoUtil.getLoginInfo(request);
        beanCopier.copy(noteContentVO,content,null);
        content.setAuthor(loginInfo.getRealName());
        content.setAuthorId(loginInfo.getId());
        content.setDeleted(0);
        if(null == content.getId()){
            content.setId(UUID.randomUUID().toString());
        }else{
            content.setGmtCreate(new Date());
            content.setGmtModified(new Date());
        }
        if(CommonConstant.FILE.equals(content.getType())){
            if(null == content.getItemId()){
                // 未分类文章统一类别
                content.setItemId(CommonConstant.BASE_ITEM);
            }
        }else{
            content.setSuffix(FileSuffixEnum.DIR);
            content.setItemId(CommonConstant.BASE_ITEM);
            content.setContent("class");
        }
        NoteContent save = mongoTemplate.save(content, MongoCollectionName.NOTE_CONTENT);
        log.info("保存（分类）日记成功，保存参【{}】",save);
        return ResultBean.successData(save);
    }

    @Override
    public ResultBean delete(HttpServletRequest request, String contentId) {
        log.info("删除（分类）日记，删除条件，id={}",contentId);
        Query query = new  Query(Criteria.where("id").is(contentId));
        List<NoteContent> noteContents = mongoTemplate.find(query, NoteContent.class);
        if(!CollectionUtils.isEmpty(noteContents)){
            noteContents.forEach(e->{
                e.setDeleted(1);
                mongoTemplate.save(e,MongoCollectionName.NOTE_CONTENT);
            });
        }

        log.info("删除结果list=[{}]",noteContents);
        return ResultBean.success("删除成功",noteContents);
    }

    @Override
    public ResultBean updateTitle(HttpServletRequest request, String contentId, String title) {
        log.info("修改标题，修改参数,contentId={},title={}",contentId,title);
        Query query = new Query(Criteria.where("id").is(contentId));
        Update update = new  Update();
        update.addToSet("title",title);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, MongoCollectionName.NOTE_CONTENT);
        log.info("修改标题结果={}",updateResult);
        return ResultBean.success("修改标题成功",updateResult);
    }

    @Override
    public ResultBean updateContent(HttpServletRequest request, String contentId, String content) {
        log.info("修改内容，修改参数,contentId={},title={}",contentId,content);
        Query query = new Query(Criteria.where("id").is(contentId));
        Update update = new  Update();
        update.addToSet("content",content);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, MongoCollectionName.NOTE_CONTENT);
        log.info("修改内容结果={}",updateResult);
        return ResultBean.success("修改标题成功",updateResult);
    }

    @Override
    public ResultBean getItem(HttpServletRequest request) {
        NoteUsers loginInfo = loginInfoUtil.getLoginInfo(request);
        Query query = new Query(Criteria.where("authorId").is(loginInfo.getId()).and("type").is(1).and("deleted").is(0));
        log.info("查询我的分类开始，query={}",query);
        List<NoteContent> noteContents = mongoTemplate.find(query, NoteContent.class, MongoCollectionName.NOTE_CONTENT);
        log.info("查询我的分类结束，查询结果={}",noteContents);
        return ResultBean.successData(noteContents);
    }

    @Override
    public ResultBean getNoteByItem(HttpServletRequest request, String itemId) {
        NoteUsers loginInfo = loginInfoUtil.getLoginInfo(request);
        Query query = new Query(Criteria.where("authorId").is(loginInfo.getId()).and("type").is(0).and("deleted").is(0).and("itemId").is(itemId));
        query.with(new Sort(Sort.Direction.DESC,"gmtCreate"));
        log.info("查询分类id={}下日记，query={}",itemId,query);
        List<NoteContent> noteContents = mongoTemplate.find(query, NoteContent.class, MongoCollectionName.NOTE_CONTENT);
        log.info("查询分类日记结束，查询结果={}",noteContents);
        return ResultBean.builder().msg(HttpStatus.OK.getReasonPhrase()).code(HttpStatus.OK.value()).data(noteContents).total(noteContents.size()).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean updateNoteTag(HttpServletRequest request, String contentId, String tagList) {
        NoteUsers loginInfo = loginInfoUtil.getLoginInfo(request);
        List<NoteTag> noteTags = null;
        try {
             noteTags = JSONArray.parseArray(tagList, NoteTag.class);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新日记标签失败，转换tagList失败,msg=[{}]",e.getMessage());
        }
        NoteContent content = noteContentDao.findById(contentId).orElse(null);
        if(null == content){
            return ResultBean.notFound("笔记内容未找到");
        }
        if(CollectionUtils.isEmpty(noteTags)){
            // 文章标签设置为空
            content.setTagList(new ArrayList<>());
        }else{
            // 循环标签是否标签库已存在，不存在添加，最后覆盖文章标签list
            try {
                noteTags.parallelStream().forEach(e->{
                    NoteTag sel = new NoteTag();
                    sel.setTitle(e.getTitle());
                    List<NoteTag> select = noteTagMapper.select(sel);
                    if (CollectionUtils.isEmpty(select)){
                        sel.setDescription(e.getDescription());
                        sel.setCreated(loginInfo.getId());
                        sel.setGmtCreate(new Date());
                        sel.setGmtModified(new Date());
                        sel.setDeleted(new Byte("0"));
                        int insert = noteTagMapper.insert(sel);
                        // 返回插入的id
                        e.setId(sel.getId());
                        log.info("文章标签保存数据=[{}],保存结果=[{}]",sel.toString(),insert);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
                log.error("标签保存数据库失败,msg=[{}]",e.getMessage());
            }

            // 保存到文章中到id和title两字段有就可以
            content.setTagList(noteTags);
            NoteContent save = noteContentDao.save(content);
            log.info("保存文章标签结束保存数据=[{}]",save.toString());

        }
        return ResultBean.success();
    }
}
