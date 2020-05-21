package com.gree.main;

import lombok.Data;

/**
 * Create by yang_zzu on 2020/5/14 on 15:50
 */
public class OrMain {

    public static void main(String[] args) {
        @Data
        class Student {
            String name;
            String age;
            String phone;

        }
        Student student = new Student();
        student.setAge("1");

        if (student.getAge().equals("0") || student.getAge().equals("1")) {
            System.out.println("000000000000000000");
        }
    }
}
