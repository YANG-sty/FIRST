package main.java.com.gree.function;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * function 接口，既有输入，也有输出
 *
 * Create by yang_zzu on 2020/4/14 on 9:06
 */
public class FunctionFunction {

    public static void main(String[] args) {

        // 定义一个 add 函数（ 该数 * 2 ）
        Function<Integer, Integer> add = i -> i + i;
        //定义一个 square 函数（ 入参的平方 ）
        Function<Integer, Integer> square = i -> i * i;

        Consumer consumer = System.out::println;

        consumer.accept(add.andThen(square).apply(2));

        Integer apply = add.apply(4);
        System.out.println("----------------->" + apply);

    }
}
