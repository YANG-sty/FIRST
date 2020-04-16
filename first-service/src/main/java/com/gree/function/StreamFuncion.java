package main.java.com.gree.function;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 * stream 流的使用
 *
 * Create by yang_zzu on 2020/4/14 on 9:15
 */
public class StreamFuncion {

    /**
     * 字符串是 字符 还是 数字
     * @param string
     * @return 字符 false ,数字 true
     */
    public static Boolean isNum(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Consumer consumer = System.out::println;

        List<String> stringList = new ArrayList<>();
        stringList.add("xiao");
        stringList.add("xiao111");
        stringList.add("xXiao");
        stringList.add("Xiao");
        stringList.add("is");
        stringList.add("123");
        stringList.add("good");
        stringList.add("student");

        // stream 流的使用
        String s = stringList.stream() //转为 Stream 流
                .filter(i -> !isNum(i)) //筛选字母型字符串
                .filter(i -> i.length() >= 2) //字符串长度 大于 2
                .map(i -> i.toLowerCase()) //全部转换为小写
                .distinct() //去重操作
                .sorted(Comparator.naturalOrder()) //字符串排序
                .collect(Collectors.joining(" "));

        consumer.accept("--------->>>>>>" + s);


        /**
         * 字符串提取字母
         */
        String string = "d154654654sfasflsjdfoi&*&$*#1321564*$&*#";
        String s1 = s.replaceAll("[^a-zA-X]", "");

        consumer.accept(s1);


    }
}
