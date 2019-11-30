package com.tz.mynote.aop;

import com.tz.mynote.common.bean.ResultBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * @author liBai
 * @Classname ControllerValidatorInterceptor
 * @Description 参数校验返回
 * @Date 2019-06-11 16:35
 */
@Aspect
@Component
public class ControllerValidatorInterceptor {

    @Around("execution(public * com.tz.mynote.note.controller..*.*(..)) && args(..,bindingResult)")
    public Object doAround(ProceedingJoinPoint pjp, BindingResult bindingResult) throws Throwable {
        Object retVal;
        if (bindingResult.hasErrors()) {
            retVal = ResultBean.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .msg(bindingResult.getAllErrors().get(0).getDefaultMessage()).data(bindingResult.getAllErrors().get(0).getCodes()).build();
        } else {
            retVal = pjp.proceed();
        }
        return retVal;
    }

}
