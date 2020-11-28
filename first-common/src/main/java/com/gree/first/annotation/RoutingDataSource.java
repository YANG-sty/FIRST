package com.gree.first.annotation;

import com.gree.first.contants.DataSourcess;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，态切库注解
 *
 * @Target(ElementType.METHOD) 表示 RoutingDataSource 只能用到 方法上面
 *
 * @Retention(RetentionPolicy.RUNTIME)
 * a) 若 Annotation 的类型为 SOURCE，则意味着：Annotation 仅存在于编译器处理期间，编译器处理完之后，该 Annotation 就没用了。
 *  如，" @Override" 标志就是一个 Annotation。
 *  当它修饰一个方法的时候，就意味着该方法覆盖父类的方法；并且在编译期间会进行语法检查！
 *  编译器处理完后，"@Override" 就没有任何作用了。
 * b) 若 Annotation 的类型为 CLASS，则意味着：编译器将 Annotation 存储于类对应的 .class 文件中，它是 Annotation 的默认行为。
 * c) 若 Annotation 的类型为 RUNTIME，则意味着：编译器将 Annotation 存储于 class 文件中，并且可由JVM读入。
 *
 * Create by yang_zzu on 2020/5/21 on 21:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RoutingDataSource {

    /**
     * 默认使用主库
     * 后面不指定的话，默认使用的是 masterDB 的数据库，进行数据的操作
     * 指定的话，使用指定的 数据库， 进行数据的操作
     */
    String value() default DataSourcess.MASTER_DB;

}
