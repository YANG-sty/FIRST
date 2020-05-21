package com.gree.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Create by yang_zzu on 2020/4/16 on 20:13
 */
public class AddMain {
    public static void main(String[] args) {
        /**
         * 调用类中的 静态方法
         */
        Integer add = AddStatic.add(1 , 2);
        Consumer consumer = System.out::println;
        consumer.accept(add);

        Map<String, String> map = new HashMap<>();
        map.put("name", "yang_zzu");
        map.put("age", "21");
        map.put("phone", "16675673726");
        Set<String> strings = map.keySet();
        String phone = "phone";
//        map.get(phone).
        consumer.accept("map.................>" + strings);
        //map 对象直接输出
        consumer.accept("--------------->" + map);
        //map 转为 JSON 对象
        Object o = JSON.toJSONString(map);
        consumer.accept("------------------>>>>>>>>>>>" + o);
        //转换为 JSONObject 对象，JSONObject是对 json 数据的一次 map 封装,可以像使用 map 一样进行使用
        JSONObject jsonObject = JSON.parseObject(String.valueOf(o));
        //操作 JSONObject 对象
        consumer.accept("-----------------JSONObject.name-------->++++++:    " + jsonObject.get("name"));
        consumer.accept("++++++++++++++++" + jsonObject);
        if (String.valueOf(jsonObject).equals("null")) {
            consumer.accept("???????????????????????????");
        } else {
            consumer.accept("----------------------》" + jsonObject);
        }

        //判断object 对象是否为空
        Object ob = null;
        if (ob == null) {
            consumer.accept("........................");
        } else {
            consumer.accept(">>>>>>>>>>>>>>>>>.ob------>" + ob);
        }


    }
}
