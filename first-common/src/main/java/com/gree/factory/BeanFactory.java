package com.gree.factory;

import java.util.ResourceBundle;

/**
 * 工厂模式 =======》 ioc 容器
 * 工厂模式升华为 ioc 模式
 * Create by yang_zzu on 2020/4/4 on 16:39
 */
public class BeanFactory {

    //加载配置文件
    private static ResourceBundle bundle;
    static{
        bundle = ResourceBundle.getBundle("instance");
    }

    public static <T> T getInstance(String key, Class<T> clazz) {
        String className = bundle.getString(key);
        try {
            return (T) Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}