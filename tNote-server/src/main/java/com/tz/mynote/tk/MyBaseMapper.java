package com.tz.mynote.tk;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author liBai
 * @Classname MyBaseMapper
 * @Description baseMapper 不能被扫描
 * @Date 2019-06-09 09:12
 */
@Repository
public interface MyBaseMapper<T> extends Mapper<T>, MySqlMapper<T>, ExampleMapper<T> {
}
