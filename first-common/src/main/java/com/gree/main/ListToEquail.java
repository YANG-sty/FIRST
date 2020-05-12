package com.gree.main;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * 两个 List 集合比较，然后进行，进行操作。。。。
 *
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

        Student student2 = new Student();
        student2.setId("2");
        student2.setSaleNo("2016");
        student2.setItem("kw1236");
        student2.setCount("00000");
        studentListDate.add(student2);

        Student student3 = new Student();
        student3.setId("3");
        student3.setSaleNo("2015");
        student3.setItem("kw123");
        student3.setCount("00000");
        studentListDate.add(student3);

        Student student4 = new Student();
        student4.setId("4");
        student4.setSaleNo("2017");
        student4.setItem("kw1236");
        student4.setCount("00000");
        studentListDate.add(student4);

        System.out.println(studentListDate.toString());

        List<Student> list = new ArrayList<>();
        Student student11 = new Student();
        student11.setId("1");
        student11.setSaleNo("2015");
        student11.setItem("kw1234");
        student11.setCount("00000");
        list.add(student11);

        Student student22 = new Student();
        student22.setId("2");
        student22.setSaleNo("2016");
        student22.setItem("kw1236");
        student22.setCount("00000");
        list.add(student22);

        Student student33 = new Student();
        student33.setId("3");
        student33.setSaleNo("2015");
        student33.setItem("kw123");
        student33.setCount("00001");
        list.add(student33);

        System.out.println(list.toString());

        Iterator iteratorData = studentListDate.iterator();
        Iterator iterator = list.iterator();
        //并集
        while (iteratorData.hasNext()) {
            Student student = (Student) iteratorData.next();
            System.out.println("++++" + student);
            for (int i = 0; i < list.size(); i++) {
                Student studentt = list.get(i);
                System.out.println("====" + studentt);
                if (studentt.getSaleNo().equals(student.getSaleNo()) && studentt.getItem().equals(student.getItem()) && studentt.getCount().equals(student.getCount())) {
                    System.out.println("数据相同，不进行操作---》》》》》》》" + student.toString());
                    iteratorData.remove();
                    list.remove(i);
                    i--;
                    /**
                     * 销售单号不存在，（肯定没有该条记录），插入操作
                     * 销售单号存在，物料编码不存在，（插入操作）
                     */
                } else if (studentt.getSaleNo().equals(student.getSaleNo()) && studentt.getItem().equals(student.getItem()) && !studentt.getCount().equals(student.getCount())) {
                    System.out.println("更新操作----->" + student.toString());
                    iteratorData.remove();
                    list.remove(i);
                    i--;
                }
            }
            /*//测试的 iterator 只能遍历一遍，一遍之后，就无法再次进行遍历操作
            while (iterator.hasNext()) {
                Student studentt = (Student) iterator.next();
                System.out.println("====" + studentt);
                if (studentt.getSaleNo().equals(student.getSaleNo()) && studentt.getItem().equals(student.getItem())) {
                    System.out.println("更新操作----->" + student.toString());
                    iteratorData.remove();
                    iterator.remove();
                    *//**
                     * 销售单号不存在，（肯定没有该条记录），插入操作
                     * 销售单号存在，物料编码不存在，（插入操作）
                     *//*
                }
            }*/

        }
        System.out.println("数据库需要删除的数据------>" + studentListDate.toString());
        System.out.println("需要插入的数据----》》》》》》" + list.toString());


        Data data = null;
        if (data == null) {
            System.out.println("+++++++++++++++++++++++++");

        }




    }
}
