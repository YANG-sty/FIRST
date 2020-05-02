package com.gree.main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Create by yang_zzu on 2020/4/27 on 18:31
 */
public class MapMain {
    public static void main(String[] args) {

        Consumer consumer = System.out::println;

        Map map = new HashMap();
        map.put("id", "223311");
        map.put("name", "小明");
        map.put("age", "21");
        map.put("phone", "110");

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("www.yang_zzu.com/token/?");
        for (Object o : map.keySet()) {
            Object object = map.get(String.valueOf(o));
            stringBuffer.append(String.valueOf(o)) ;
            stringBuffer.append("=") ;
            stringBuffer.append(String.valueOf(object));
            stringBuffer.append("&");
        }
        if (stringBuffer.substring(stringBuffer.length()-1).equals("&")) {
            String a = stringBuffer.substring(0, stringBuffer.length() - 1);
            consumer.accept("----------->>>>>" + a);
        }
        String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
        consumer.accept("------------->>>>>>>>>>>>>>" + substring);


    }
}
