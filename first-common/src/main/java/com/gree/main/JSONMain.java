package com.gree.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import freemarker.template.utility.StringUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by yang_zzu on 2020/4/30 on 17:13
 */
public class JSONMain {
    public static void main(String[] args) {

        @Data
        class Student {
            String name;
            String age;
            String phone;

        }
        Student student = new Student();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            student.setName("a" + i);
            student.setAge("b" + i);
            student.setPhone("c" + i);
            students.add(student);
        }
        Object o = JSON.toJSON(students);
        String s = String.valueOf(o);

        System.out.println("JSON.toJSON---->" + s);

        String s2 = JSON.toJSONString(student);
        System.out.println("JSON.toJSONString---->" + s2);



        /**
         *  Json 字符串转 JSONArray
         */
        JSONArray jsonArray = JSONArray.parseArray(s);
        List<Student> students1 = new ArrayList<>();
        for (Object o1 : jsonArray) {
            String s1 = String.valueOf(o1);
            Student student1 = JSON.parseObject(s1, Student.class);
            students1.add(student1);
        }
        System.out.println(students1.toString());


        /**
         * 逻辑运算符 |操作  &操作
         */
        boolean a = true;
        boolean b = false;
        boolean c = true;
        boolean d = false;

        boolean b1 = a | b;
        System.out.println(b1);
        boolean b2 = a | c;
        System.out.println(b2);
        boolean b5 = b | d;
        System.out.println(b5);

        boolean b3 = a & b;
        System.out.println(b3);
        boolean b4 = a & c;
        System.out.println(b4);
        boolean b6 = b & d;
        System.out.println(b6);

        int i = 1;
        if (i == 1) {
            System.out.println("------>>>" + i);
            i++;
        }
        System.out.println(i);


        /**
         * 空格，半角，圆角
         * trim()默认清除的是 半角空格
         */
        System.out.println("----------------------------------");
        String banJiao = " xiao ming is ";
        System.out.println("----->" + banJiao.length());
        String trim1 = StringUtils.trim(banJiao);
        System.out.println("----->" + trim1.length());

        System.out.println("###################################");
        String yuanJiao = "　xiao　ming　is　";
        System.out.println("----->" + yuanJiao.length());
        String trim2 = StringUtils.trim(yuanJiao);
        System.out.println("----->" + trim2.length());
        String replace = yuanJiao.replace("　", "");
        System.out.println("----->" + replace.length());
        System.out.println();


    }
}
