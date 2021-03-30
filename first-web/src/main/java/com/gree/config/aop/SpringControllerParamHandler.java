package com.gree.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
@Slf4j
public class SpringControllerParamHandler {

    /**
     * 请求参数预处理
     */

    @InitBinder
    public void globalInitBinder(WebDataBinder binder) {
        System.out.println("execute ----- com.gree.config.aop.SpringControllerAdvice.globalInitBinder");
        /*
         * 创建一个字符串微调编辑器
         * 参数{boolean emptyAsNull}: 是否把空字符串("")视为 null
         */
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);

        /*
         * 注册自定义编辑器
         * 接受两个参数{Class<?> requiredType, PropertyEditor propertyEditor}
         * requiredType：所需处理的类型
         * propertyEditor：属性编辑器，StringTrimmerEditor就是 propertyEditor的一个子类
         */
        binder.registerCustomEditor(String.class, trimmerEditor);

        /**
         * 注册自定义编辑器
         * 同上，这里就不再一步一步讲解了
         */
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
    }

}