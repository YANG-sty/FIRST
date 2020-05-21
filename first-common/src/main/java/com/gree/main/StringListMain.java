package com.gree.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by yang_zzu on 2020/4/27 on 20:00
 */
public class StringListMain {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("110");
        list.add("120");
        list.add("112");
        list.add("119");

        for (String s : list) {
            System.out.println(s);
        }
        String s = list.toString();
        System.out.println(s);

        String s1 = JSON.toJSONString(list);
        System.out.println(s1);

        Object object = JSON.parseObject(s1, Object.class);

        System.out.println(object);

    }
}
