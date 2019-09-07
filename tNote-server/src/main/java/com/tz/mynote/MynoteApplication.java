package com.tz.mynote;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@MapperScan("com.tz.mynote.dao")
@SpringBootApplication
public class MynoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MynoteApplication.class, args);
    }

}
