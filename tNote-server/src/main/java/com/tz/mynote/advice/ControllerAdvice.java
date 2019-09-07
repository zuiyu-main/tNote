package com.tz.mynote.advice;

import com.tz.mynote.common.bean.ResultBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tz
 * @Classname ControllerAdvice
 * @Description 全局异常处理
 * @Date 2019-09-07 17:53
 */
@org.springframework.web.bind.annotation.ControllerAdvice
@RestController
public class ControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResultBean errorHandler(Exception e){
        e.printStackTrace();
        return  ResultBean.builder()
                .msg(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .data(e.getMessage()).build();
    }
}
