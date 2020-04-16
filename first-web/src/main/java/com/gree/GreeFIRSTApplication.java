package com.gree;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Create by yang_zzu on 2020/4/14 on 15:16
 */
@SpringBootApplication(scanBasePackages = "com.gree")
@EnableScheduling
@EnableEncryptableProperties
public class GreeFIRSTApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(GreeFIRSTApplication.class, args);
    }
}
