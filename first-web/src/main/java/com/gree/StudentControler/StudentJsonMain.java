package com.gree.StudentControler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gree.first.user.daomain.Student;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Create by yang_zzu on 2020/4/21 on 12:16
 */
public class StudentJsonMain {

    public static void main(String[] args) {
        Student student = new Student();
        int i = 123;
        student.setId(String.valueOf(i));
        student.setAddress("地球+" + i);
        student.setAge(18);
        student.setPhone("11022223333");
        student.setEmail("diqiu@uu.ccc");
        student.setIdCar(UUID.randomUUID().toString());

        String s = JSON.toJSONString(student);
        Consumer consumer = System.out::println;
        consumer.accept(s);

        JSONObject jsonObject = JSONObject.parseObject(s);
        consumer.accept(jsonObject);

    }
}
