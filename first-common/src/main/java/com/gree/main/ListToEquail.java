package com.gree.main;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by yang_zzu on 2020/5/2 on 15:26
 */
public class ListToEquail {

    @Data
    static class Student{
        private String id;
        private String saleNo;
        private String item;
        private String count;
    }

    public static void main(String[] args) {
        List<Student> studentListDate = new ArrayList<>();
        Student student1 = new Student();
        student1.setId("1");
        student1.setSaleNo("2015");
        student1.setItem("kw1234");
        student1.setCount("00000");
        studentListDate.add(student1);

        student1.setId("2");
        student1.setSaleNo("2016");
        student1.setItem("kw1236");
        student1.setCount("00000");
        studentListDate.add(student1);

        student1.setId("3");
        student1.setSaleNo("2015");
        student1.setItem("kw123");
        student1.setCount("00000");
        studentListDate.add(student1);

        student1.setId("4");
        student1.setSaleNo("2017");
        student1.setItem("kw1236");
        student1.setCount("00000");
        studentListDate.add(student1);

        System.out.println(studentListDate.toString());

        List<Student> list = new ArrayList<>();
        student1.setId("1");
        student1.setSaleNo("2015");
        student1.setItem("kw1234");
        student1.setCount("00000");
        list.add(student1);

        student1.setId("2");
        student1.setSaleNo("2016");
        student1.setItem("kw1236");
        student1.setCount("00000");
        list.add(student1);

        student1.setId("3");
        student1.setSaleNo("2015");
        student1.setItem("kw123");
        student1.setCount("00000");
        list.add(student1);

        for (Student student : studentListDate) {
            for (Student student2 : list) {
                if (student2.getSaleNo().equals(student.getSaleNo()) && student2.getItem().equals(student.getItem())) {
                    System.out.println("更新操作----->" + student.toString());
                    studentListDate.remove(student);
                    /**
                     * 销售单号不存在，（肯定没有该条记录），插入操作
                     * 销售单号存在，物料编码不存在，（插入操作）
                     */
                } else if (student2.getSaleNo().equals(student.getSaleNo()) && !student2.getItem().equals(student.getItem())) {
                    System.out.println("插入操作----》" + student.toString());
                } else if (!student2.getSaleNo().equals(student.getSaleNo()) && !student2.getItem().equals(student.getItem())) {
                    System.out.println("插入操作----》》》》》》" + student.toString());
                }
            }
        }


    }
}
