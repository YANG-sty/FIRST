package com.gree.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * 字符串时间转>>>> Instant 类型，手动转
 * Create by yang_zzu on 2020/4/8 on 11:36
 */
public class TimeTypeUitls {

    public Instant StringToInstant(String string) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(string);
        Instant instant = date.toInstant();
        return instant;


    }

    public static void main(String[] args) throws ParseException {

        String time1 = "2020-03-23 17:13:25";
        String time2 = "2020-03-23 17:13:25";
        String time3 = "2020-03-23 17:13:25";
        String time4 = "2020-03-23 17:13:25";
        List<String> timeList = new ArrayList<>();
        timeList.add(time1);
        timeList.add(time2);
        timeList.add(time3);
        timeList.add(time4);

        TimeTypeUitls timeTypeUitls = new TimeTypeUitls();
        for (String s : timeList) {
            Instant instant = timeTypeUitls.StringToInstant(s);
            System.out.println(s + ">>>>>>>>>>>>>>>>>>>>" + instant);
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String parse = simpleDateFormat.format(date);
        System.out.println(parse);

    }
}
