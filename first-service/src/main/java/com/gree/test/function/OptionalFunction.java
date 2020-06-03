package com.gree.test.function;


import com.gree.first.user.daomain.Student;
import com.gree.first.user.daomain.Subject;

import java.util.Optional;

/**
 * optional 接口
 * 本质是一个容器，有输入，有输出
 * Create by yang_zzu on 2020/4/13 on 10:49
 */
public class OptionalFunction {

    public static void main(String[] args) {

        Student student = new Student();
        student.setId("20200413");
        student.setName("小明");
        student.setAge(20);

        Subject subject = new Subject();
        subject.setYuwen(99);
        subject.setShuxue(100);
        student.setSubject(subject);
        System.out.println(student.toString());

        /**
         * 1.判断对象是否为空
         * 2.获取对象中的属性信息
         */
        String s = Optional.ofNullable(student)
                .map(Student::getName)
                .orElse(null);
        Double aDouble = Optional.ofNullable(student)
                .map(Student::getSubject)
                .map(Subject::getShuxue)
                .orElse(0.0);

        System.out.println("-------name------->" + s);
        System.out.println("-------成绩---数学---->" + aDouble);

    }

}
