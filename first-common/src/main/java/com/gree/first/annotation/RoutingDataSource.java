package com.gree.first.annotation;

import com.gree.first.contants.DataSourcess;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态切库注解
 *
 * Create by yang_zzu on 2020/5/21 on 21:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RoutingDataSource {

    /**
     * 默认使用主库
     * 后面不指定的话，默认使用的是 masterDB 的数据库，进行数据的操作
     * 指定的话，使用指定的 数据库， 进行数据的操作
     */
    String value() default DataSourcess.MASTER_DB;

}
