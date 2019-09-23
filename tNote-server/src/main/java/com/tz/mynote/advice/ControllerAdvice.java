package com.tz.mynote.advice;

import com.alibaba.fastjson.JSON;
import com.tz.mynote.common.bean.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tz
 * @Classname ControllerAdvice
 * @Description 全局异常处理
 * @Date 2019-09-07 17:53
 */
@Slf4j
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
//    /**
//     * 定义参数异常处理器.参数校验通过一场捕捉的方式返回校验结果
//     *
//     * @param e 当前平台异常参数对象.
//     * @return org.springframework.http.ResponseEntity
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<Map<String, Object>> validateErrorHandler(BindException e) {
//        Map<String, Object> errorMap = new HashMap<>(2);
//        BindingResult bindingResult = e.getBindingResult();
//        if (bindingResult.hasErrors()) {
//            List<FieldError> errorList = bindingResult.getFieldErrors();
//            String errorMsg = "[字段:" + errorList.get(0).getField() + "]错误，原因:" + errorList.get(0).getDefaultMessage();
//            errorMap.put("message", errorMsg);
//            errorMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
//            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
//        }
//        log.error("[服务] - [捕获参数异常。异常信息:{}]", JSON.toJSONString(bindingResult));
//        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
//    }
}
