package com.gree.designpattern;

import org.springframework.stereotype.Service;

/**
 * Create by yang_zzu on 2020/5/12 on 9:36
 */
public class DecoratorModeServiceImplFirst extends DecoratorModeService {
    @Override
    public void printDecoratrorMode() {
        System.out.println("Bitter Coffee");
    }
}
