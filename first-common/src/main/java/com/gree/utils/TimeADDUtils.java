package com.gree.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Create by yang_zzu on 2020/4/5 on 8:57
 */
public class TimeADDUtils {

    /**
     * 对时间进行加减操作
     * @param args
     */
    public static void main(String[] args) {

        //对date 时间进行格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间
        Date date = new Date();
        String dateString = simpleDateFormat.format(date);
        System.out.println("---------------->" + dateString);
        //获得一小时二十分钟 前 的时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //如果要之后的时间，则将后面的参数变为正数，里面还有其他的年月日等的操作，具体看自己的需求
        calendar.add(Calendar.HOUR,-1);
        calendar.add(Calendar.MINUTE,-20);
        Date time = calendar.getTime();
        String format = simpleDateFormat.format(time);
        System.out.println("--------------------->" + format);

    }
}
