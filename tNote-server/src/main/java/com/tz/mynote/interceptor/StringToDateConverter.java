package com.tz.mynote.interceptor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tz
 * @Classname StringToDateConverter
 * @Description string 转换date
 * @Date 2019-09-09 16:34
 */
public class StringToDateConverter implements Converter<String, Date>{
    @Override
    public Date convert(String s) {
        Date target = null;
        if(!StringUtils.isEmpty(s)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                target =  format.parse(s);
            } catch (ParseException e) {
                throw new RuntimeException(String.format("parser %s to Date fail", s));
            }
        }
        return target;
    }
}
