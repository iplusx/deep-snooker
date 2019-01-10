package com.snooker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author WangJunyu
 * @date 16/11/2
 * @e-mail junyu.wang147@gmail.com
 * @description spring boot 入口
 */
@EnableScheduling
@SpringBootApplication
@PropertySource(value = "file:application.yml")
public class SnookerApiJava {
    public static void main(String[] args) {
        SpringApplication.run(SnookerApiJava.class, args);
    }
}
