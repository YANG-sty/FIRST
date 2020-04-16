package com.gree.function;

import com.gree.user.daomain.Student;

import java.util.function.Consumer;

/**
 * Consumer 消费者，
 *
 * Create by yang_zzu on 2020/4/13 on 21:18
 */
public class ConsumerFunction {

    public static void main(String[] args) {
        Student student = new Student();
        main.java.com.gree.user.daomain.Subject subject = new main.java.com.gree.user.daomain.Subject();

        student.setId("2020041305");
        student.setName("小红");
        student.setAge(21);

        Consumer consumer = System.out::println;

        consumer.accept(student.getSubject());
        consumer.accept(student.getId());
        consumer.accept(student.getName());

        //连续打印 3 次
        consumer.andThen(consumer).andThen(consumer).accept(student.getName());

    }
}
