package com.gree.designpattern;

import com.google.inject.internal.util.$Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设计者模式控制层
 * Create by yang_zzu on 2020/5/12 on 9:23
 */
@Api(value = "设计者模式接口", tags = "和value的作用一样，使用一个就可以")
@RestController
@RequestMapping("/api/designPattern")
@Slf4j
public class DesignPatternController {

    DecoratorModeService decoratorModeService;

    @PostMapping("/decoratorMode")
    @ApiOperation(value = "装饰者模式", httpMethod = "post", response = String.class, notes = "接口发布说明")
    @ApiParam(required = true, name = "String", value = "字符串")
    public String decoratorMode(String s) {
        decoratorModeService.printDecoratrorMode();
        return "decoratorMode";
    }

}
