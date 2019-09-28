package com.tz.mynote.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.tz.mynote.bean.NoteUsers;
import com.tz.mynote.bean.VO.NoteContentVO;
import com.tz.mynote.bean.mongo.NoteContent;
import com.tz.mynote.common.bean.ResultBean;
import com.tz.mynote.constant.CommonConstant;
import com.tz.mynote.constant.MongoCollectionName;
import com.tz.mynote.dao.mongo.NoteContentDao;
import com.tz.mynote.service.NoteContentService;
import com.tz.mynote.util.LoginInfoUtil;
import com.tz.mynote.util.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
    private static BeanCopier beanCopier = BeanCopier.create(NoteContentVO.class,NoteContent.class,false);
    private static SnowFlakeUtil SNOWFLAKEUTIL = new SnowFlakeUtil(1, 9);

    @Override
    public ResultBean test() {
        NoteContent content = new NoteContent();
        content.setId(SNOWFLAKEUTIL.nextId());
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
    public ResultBean<NoteContent> save(HttpServletRequest request, NoteContentVO noteContentVO) {
        log.info("保存（分类）日记，接受参数=【{}】",noteContentVO);
        NoteContent content = new NoteContent();
        NoteUsers loginInfo = loginInfoUtil.getLoginInfo(request);
        beanCopier.copy(noteContentVO,content,null);
        content.setAuthor(loginInfo.getRealName());
        content.setAuthorId(loginInfo.getId());
        content.setDeleted(0);
        content.setId(SNOWFLAKEUTIL.nextId());
        if(CommonConstant.FILE.equals(content.getType())){
            if(null == content.getItemId()){
                // 未分类文章统一类别
                content.setItemId(CommonConstant.BASE_ITEM);
            }
        }else{
            content.setItemId(CommonConstant.BASE_ITEM);
        }
        content.setContent("class");
        content.setGmtCreate(new Date());
        content.setGmtModified(new Date());
        NoteContent save = mongoTemplate.save(content, MongoCollectionName.NOTE_CONTENT);
        log.info("保存（分类）日记成功，保存参【{}】",save);
        return ResultBean.successData(save);
    }

    @Override
    public ResultBean<NoteContent> delete(HttpServletRequest request, String contentId) {
        log.info("删除（分类）日记，删除条件，id={}",contentId);
        Query query = new  Query(Criteria.where("id").is(contentId));
        DeleteResult remove = mongoTemplate.remove(query,MongoCollectionName.NOTE_CONTENT);
        log.info("删除结果=[{}]",remove);
        return ResultBean.success("删除成功",remove);
    }

    @Override
    public ResultBean<NoteContent> updateTitle(HttpServletRequest request, String contentId, String title) {
        log.info("修改标题，修改参数,contentId={},title={}",contentId,title);
        Query query = new Query(Criteria.where("id").is(contentId));
        Update update = new  Update();
        update.addToSet("title",title);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, MongoCollectionName.NOTE_CONTENT);
        log.info("修改标题结果={}",updateResult);
        return ResultBean.success("修改标题成功",updateResult);
    }

    @Override
    public ResultBean<NoteContent> updateContent(HttpServletRequest request, String contentId, String content) {
        log.info("修改内容，修改参数,contentId={},title={}",contentId,content);
        Query query = new Query(Criteria.where("id").is(contentId));
        Update update = new  Update();
        update.addToSet("content",content);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, MongoCollectionName.NOTE_CONTENT);
        log.info("修改内容结果={}",updateResult);
        return ResultBean.success("修改标题成功",updateResult);
    }

    @Override
    public ResultBean<NoteContent> getItem(HttpServletRequest request) {
        NoteUsers loginInfo = loginInfoUtil.getLoginInfo(request);
        Query query = new Query(Criteria.where("authorId").is(loginInfo.getId()).and("type").is(1).and("deleted").is(0));
        log.info("查询我的分类开始，query={}",query);
        List<NoteContent> noteContents = mongoTemplate.find(query, NoteContent.class, MongoCollectionName.NOTE_CONTENT);
        log.info("查询我的分类结束，查询结果={}",noteContents);
        return ResultBean.successData(noteContents);
    }

    @Override
    public ResultBean<NoteContent> getNoteByItem(HttpServletRequest request, String itemId) {
        NoteUsers loginInfo = loginInfoUtil.getLoginInfo(request);
        Query query = new Query(Criteria.where("authorId").is(loginInfo.getId()).and("type").is(1).and("deleted").is(0));
        log.info("查询分类id={}下日记，query={}",itemId,query);
        List<NoteContent> noteContents = mongoTemplate.find(query, NoteContent.class, MongoCollectionName.NOTE_CONTENT);
        log.info("查询分类日记结束，查询结果={}",noteContents);
        return ResultBean.successData(noteContents);
    }
}
