package com.gree.first.utils;

import com.gree.first.contants.DataSourcess;
import lombok.extern.slf4j.Slf4j;

/**
 * 管理数据源连接
 * Create by yang_zzu on 2020/5/27 on 11:38
 */
@Slf4j
public class DataSourceContextHolder {
    //默认数据源
    public static final String DEFAULT_DATASOURCE = DataSourcess.MASTER_DB;

    private static final ThreadLocal<String> threadDB = new ThreadLocal<>();

    /**
     * 切换数据源
     * @param db
     */
    public static void setDB(String db) {
        log.debug("切换到数据源{}", db);
        threadDB.set(db);
    }

    /**
     * 得到数据源
     * @return
     */
    public static String getDB() {
        log.debug("获得的数据源{}", threadDB.get());
        return threadDB.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDB() {
        threadDB.remove();
        log.debug("切换到默认数据源");

    }
}
