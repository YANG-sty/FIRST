package com.gree.testutils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * BigDecimal 数据类型的使用
 * Create by yang_zzu on 2020/4/5 on 10:12
 */
public class BigDecimalUtils {
    /**
     * 一般在进行数据的获取的时候直接使用 Double double = bigDecimal.doubleValue();
     * 获得是该数据的正常值，后面可以正常的对 dobule 类型的数据进行操作。
     * 一般在对 BigDecimal 数据进行操作的时候，在不考虑过多的精度的情况下，
     * 如果精度在小数点后10 ，则转化为 double 然后进行操作，则可以保证数据的精度
     * 再多的话没有测试过，有兴趣的话可以自己测试一下。
     *
     * 推荐使用这种方式初始化
     * BigDecimal bigDecimal2 = new BigDecimal("1.923");
     *
     * 这种方式会导致数据后面自动补全剩余的位数
     * BigDecimal bigDecimal = new BigDecimal(0.15260100200000);
     *
     * 0.15260100200000（影响精度的情况）
     * ------------bigdecimal==>double==>String%--------------->15.260100200000002%
     *
     */
    /**
     * 转成 int （小数点后面的数据会舍弃）
     * 没有 四舍五入
     * @param bigDecimal
     * @return  int
     */
    public int toInt(BigDecimal bigDecimal) {
        int i = bigDecimal.intValue();
        return i;
    }

    /**
     * 会影响精度，一般在在小数点后 10 位左右
     * @param bigDecimal
     * @return double
     */
    public double toDouble(BigDecimal bigDecimal) {
        double v = bigDecimal.doubleValue();
        return v;
    }


    /**
     * int, double 转 BigDecimal
     * @param o int，dobule
     * @return bigDecimal
     */
    public BigDecimal toBigDecimal(Object o) {
        String s = String.valueOf(o);
        Double aDouble = Double.valueOf(s);
        BigDecimal bigDecimal = BigDecimal.valueOf(aDouble);
        return bigDecimal;
    }




    public static void main(String[] args) {
        //直接用dobule类型的数据进行新建,
        BigDecimal bigDecimal = new BigDecimal(0.15260100200000);
        System.out.println("----------固定dobule--------->" + bigDecimal);

        //使用字符串类型创建新的BigDecimal数据
        BigDecimal bigDecimal1 = new BigDecimal("0.15260702");
        System.out.println("--------------String------------------>" + bigDecimal1);

        //BigDecimal转化成 百分数，这种方式会影响数据的精度,会进行四舍五入
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        DecimalFormat decimalFormat1 = new DecimalFormat("0.000%");
        String format = decimalFormat.format(bigDecimal);
        System.out.println("---------------直接转百分数，保留两位小数--------------->" + format);
        String format1 = decimalFormat1.format(bigDecimal1);
        System.out.println("---------------直接转百分数，保留 3 位小数--------------->" + format1);

        /**
         * 对BigDecimal 进行运算的时候推荐使用这种方式
         *
         * BigDecimal转化成百分数，先转为 double 类型的数据----》再 * 100
         */
        double doubleValue = bigDecimal.doubleValue();
        double v = bigDecimal1.doubleValue();
        System.out.println("------>" + doubleValue);
        System.out.println("------>" + v);
        double sdobuleValue = doubleValue * 100;
        System.out.println("------------bigdecimal==>double==>String%--------------->" + sdobuleValue + "%");

        /**
         * 直接对 BigDecimal 进行运算的时候，都需要是同种类型的数据
         *
         * 加法：add
         *
         * 减法：subtract
         *
         * 乘法：multiply
         *
         * 除法：divide
         */
        //BigDecimal 类型进行四则运算
        int a = 213;
        double b = 12.435430025006700;
        BigDecimal abig = BigDecimal.valueOf(a);
        BigDecimal bbig = BigDecimal.valueOf(b);
        System.out.println("a=====>"+abig);
        System.out.println("b=====>"+bbig);
        // * 100
        int cheng = 100;
        BigDecimal multiply = abig.multiply(BigDecimal.valueOf(cheng));
        System.out.println("a * b = " + multiply);
        BigDecimal multiply1 = bbig.multiply(BigDecimal.valueOf(cheng));
        System.out.println("a * b = " + multiply1);
        BigDecimal multiply2 = bbig.multiply(BigDecimal.valueOf(100));
        System.out.println("a * b = " + multiply2);


        BigDecimal bigDecimal2 = new BigDecimal("1.923");
        int i = bigDecimal2.intValue();
        System.out.println(i);
        System.out.println(bigDecimal.toString());

        BigDecimalUtils bigDecimalUtils = new BigDecimalUtils();
        BigDecimal bigDecimal3 = bigDecimalUtils.toBigDecimal("1001");
        BigDecimal bigDecimal4 = bigDecimalUtils.toBigDecimal(1001.0000123);
        BigDecimal bigDecimal5 = bigDecimalUtils.toBigDecimal(1001);
        System.out.println(bigDecimal3 + "-----------" + bigDecimal4 + "-------------------" + bigDecimal5);


    }

}
