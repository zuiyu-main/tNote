package com.tz.mynote.util;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * @author https://github.com/TianPuJun @无痕
 * @ClassName ClassUtils
 * @Description 反射工具类
 * @Date 18:03 2020/3/17
 **/
public class BeanUtils {

    /**
     * target->source
     * target 字段值不为空就set source
     * @param source 拷贝到到目标
     * @param target 要拷贝的数据源
     * @throws IllegalAccessException
     */
    public static void ifNullSet(Object source,Object target) throws IllegalAccessException {
        Class aClass = target.getClass();
        Class<?> sourceClass = source.getClass();
        Field[] declaredFields1 = sourceClass.getDeclaredFields();
        Field[] declaredFields = aClass.getDeclaredFields();
        for(Field f:declaredFields){
            f.setAccessible(true);
            if (!StringUtils.isEmpty(f.get(target))) {
                for (Field field : declaredFields1) {
                    field.setAccessible(true);
                    if(field.getName().equals(f.getName())){
                        field.set(source,f.get(target));
                    }
                }
            }
        }
    }

}
