package com.gree.main;

/**
 * Create by yang_zzu on 2020/4/16 on 20:12
 */
public class AddStatic {

    public static Integer add(Integer a, Integer b) {
        int i = a + b;
        return i;
    }

    public static void main(String[] args) {
        double a = 1.678;
        double b = 0.808;
        double c = 0.365;

        double x = a * b * c;
        String format = String.format("%.3f", x);
        System.out.println(x);
        System.out.println(format);
    }

}
