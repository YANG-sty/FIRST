package com.gree.designpattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Create by yang_zzu on 2020/5/12 on 9:12
 */
@Service
public class DecoratorModeServiceImpl extends DecoratorModeService{

    private DecoratorModeService decoratorModeService;


    public DecoratorModeServiceImpl(DecoratorModeServiceImplFirst decoratorModeServiceImplFirst) {
        this.printDecoratrorMode();
    }


    @Override
    public void printDecoratrorMode() {
        decoratorModeService.printDecoratrorMode();
    }
}
