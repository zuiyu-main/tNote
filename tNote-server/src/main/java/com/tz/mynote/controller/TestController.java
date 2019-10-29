package com.tz.mynote.controller;

import com.tz.mynote.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liBai
 * @Classname TestController
 * @Description TODO
 * @Date 2019-06-09 09:19
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;
    @RequestMapping("/hello")
    public Object hello(){
        return testService.hello();
    }
    @GetMapping("/error")
    public String error(){
        int i = 1/0;
        return "sdf";
    }
}
