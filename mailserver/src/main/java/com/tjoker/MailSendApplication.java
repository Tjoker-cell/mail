package com.tjoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 十字街头的守候
 */
@EnableScheduling //开启基于注解的定时任务
@SpringBootApplication
public class MailSendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailSendApplication.class, args);
    }

}
