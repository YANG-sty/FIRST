package com.gree.test.function;

import com.gree.first.user.domain.Student;
import com.gree.first.user.domain.Subject;

import java.util.function.Consumer;

/**
 * Consumer 消费者，
 *
 * Create by yang_zzu on 2020/4/13 on 21:18
 */
public class ConsumerFunction {

    public static void main(String[] args) {
        Student student = new Student();
        Subject subject = new Subject();

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
