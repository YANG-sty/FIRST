package com.gree.main;

import java.util.function.Consumer;

/**
 * Create by yang_zzu on 2020/4/15 on 16:46
 */
public class Switch {

    public static String switchCase(Integer integer) {
        /**
         * 因为在 case 中使用的是 return，执行完之后，直接进行返回，
         * 不会进行之后的操作
         * 不用使用break 进行跳出
         * 反而，在增加 break 之后会导致，报错。
         */
        switch (integer) {
            case 1: return "11";
            case 2: return "22";
            case 3: return "33";
            case 4: return "44";
            case 5: return "55";
            case 6: return "66";
            case 7: return "77";
            case 8: return "88";
            default: return "";
        }
    }

    public static void main(String[] args) {


        int a = 5;
        String s = switchCase(a);
        Consumer consumer = System.out::println;
        consumer.accept("------------------>>>>>>>>>>>>>>>>>>>>>>" + s);

    }
}
