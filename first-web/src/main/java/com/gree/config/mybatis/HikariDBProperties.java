package com.gree.config.mybatis;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create by yang_zzu on 2020/4/15 on 20:39
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class HikariDBProperties {
    private HikariDataSource masterDB;
    private HikariDataSource secendDB;
    private HikariDataSource thirdDB;
}
