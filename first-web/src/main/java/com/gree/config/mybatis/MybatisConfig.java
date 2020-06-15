package com.gree.config.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.google.common.collect.Maps;
import com.gree.first.contants.DataSourcess;
import com.gree.first.utils.DynamicDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

/**
 * mybatis-plus 配置
 * <p>
 * Create by yang_zzu on 2020/5/22 on 9:31
 */
@MapperScan(basePackages = {"com.gree.first.*.dao"})
@Configuration
//order 表示 事务的执行顺序，引入了 多数据源，所以让spring事务的aop在多数据源的aop后面
@EnableTransactionManagement(order = 2)
public class MybatisConfig {

    @Autowired
    HikariDBProperties hikariDBProperties;

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * SQL执行效率插件
     * @return
     */
    @Bean
    //dev环境开启
    @Profile("dev")
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * 配置多数据源
     * @return
     */
    @Bean(name="dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(hikariDBProperties.getMasterDB());

        //配置多数据源
        Map<Object, Object> dsMap = Maps.newHashMap();
        /**
         * 在这里配置的数据源必须都是存在，并且能够正常使用，否则系统启动会失败
         * 将配置无效的数据源进行注释，能够正常的使用系统。
         */
        dsMap.put(DataSourcess.MASTER_DB, hikariDBProperties.getMasterDB());
//        dsMap.put(DataSourcess.SECEND_DB, hikariDBProperties.getSecendDB());
//        dsMap.put(DataSourcess.THIRD_DB, hikariDBProperties.getThirdDB());

        dynamicDataSource.setTargetDataSources(dsMap);

        return dynamicDataSource;

    }

    /**
     * 配置事务管理器
     * @return
     */
    @Bean
    DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dynamicDataSource());
        return dataSourceTransactionManager;
    }


}
