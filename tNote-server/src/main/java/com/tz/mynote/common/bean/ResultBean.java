package com.tz.mynote.common.bean;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

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

    public static ResultBean success(){
        return ResultBean.builder().msg(HttpStatus.OK.getReasonPhrase()).code(HttpStatus.OK.value()).build();
    }
    public static ResultBean successMsg(String msg){
        return ResultBean.builder().msg(msg).code(HttpStatus.OK.value()).build();
    }
    public static ResultBean success(String msg,Object data){
        return ResultBean.builder()
                .msg(msg)
                .code(HttpStatus.OK.value())
                .data(data).build();
    }
    public static  ResultBean<Object> successData(Object data){
        return ResultBean.builder()
                .msg(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .data(data).build();
    }
    public static ResultBean notFound(String msg){
        return ResultBean.builder().msg(msg).code(HttpStatus.NOT_FOUND.value()).build();
    }
}
