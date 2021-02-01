package com.gree.monit;

import com.gree.first.moniter.MonitLog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangLongFei 2021-02-01-16:14
 */
public class MonitListContent {
    /**
     * todo 用于保存，用户操作的信息，暂时保存到内存中，之后写到数据库中
     */
    public static volatile List<MonitLog> monitLogList = new ArrayList<>();
}
