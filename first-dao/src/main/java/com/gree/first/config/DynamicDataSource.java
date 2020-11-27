package com.gree.first.config;

import com.gree.first.utils.DataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * 动态数据源切换
 * Create by yang_zzu on 2020/5/27 on 9:28
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        //获得数据源
        return DataSourceContextHolder.getDB();
    }
}
