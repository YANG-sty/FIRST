package com.gree;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Create by yang_zzu on 2020/4/14 on 15:16
 */
@SpringBootApplication(scanBasePackages = "com.gree")
@EnableScheduling
@EnableEncryptableProperties
@EnableRetry//启动重试机制
public class GreeFIRSTApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(GreeFIRSTApplication.class, args);
    }
}
