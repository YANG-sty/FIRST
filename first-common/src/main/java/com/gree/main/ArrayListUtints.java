package com.gree.main;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Create by yang_zzu on 2020/4/11 on 9:21
 */
public class ArrayListUtints {

    public static void main1(String[] args) {
        List<Map> mapList = new ArrayList<>();
        Map map = new HashMap();
        map.put("name", "小明");
        map.put("age", "12");
        map.put("phone", "12233334444");

        mapList.add(map);

        System.out.println(JSON.toJSONString(mapList));
    }

    public String retailsign(String s) {
        if (StringUtils.isBlank(s) || "null".equals(s)) {
            return "1";
        }
        int i = Integer.parseInt(s);
        if (i == 0) {
            return "是";
        } else {
            return "否";
        }
    }

    public static void main(String[] args) throws IOException {
        String s = null;
        ArrayListUtints arrayListUtints = new ArrayListUtints();
        String retailsign = arrayListUtints.retailsign(s);
        System.out.println(retailsign);

        Date data = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(data);
        System.out.println("------------>" + format);

        Pattern ppp = compile(":");
        Matcher m = ppp.matcher(format);
        String tmp = m.replaceAll("_");


        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\180619\\Desktop\\" + "tesst_" + tmp + ".pdf");

        fileOutputStream.close();

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        String a = "";
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                a = a + list.get(i) + "_";
            } else {
                a = a + list.get(i);
            }
        }
        System.out.println(a);

    }
}
