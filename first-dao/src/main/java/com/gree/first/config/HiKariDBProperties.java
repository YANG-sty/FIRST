package com.gree.first.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * HiKari 多数据源配置，获得 Properties 中的配置信息
 * @author yangLongFei 2020-11-27-19:26
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Getter
@Setter
public class HiKariDBProperties {
    private HikariDataSource masterDB;
    private HikariDataSource secendDB;
    private HikariDataSource thirdDB;
}
