package com.tz.mynote.common.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author tz
 * @Classname ResultBean
 * @Description 统一返回结果
 * @Date 2019-09-03 14:45
 */
@Data
@Builder
public class ResultBean<T> {
    private int code;
    private String msg;
    private Integer total;
    private T data;
}
