package com.home.examination;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan({"com.home.examination.mapper"})
@SpringBootApplication
public class ExaminationApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ExaminationApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ExaminationApplication.class, args);
    }

}