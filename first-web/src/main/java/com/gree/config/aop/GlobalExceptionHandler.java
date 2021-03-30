package com.gree.config.aop;

import com.gree.first.utils.HttpContextUtils;
import com.gree.first.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangLongFei 2021-03-29-16:48
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * ExceptionHandler 匹配到一个后，后面的不会再进行匹配操作，谁最先匹配到，执行谁
     */

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public String handleZeroException(Exception e) {
        return "java.lang.ArithmeticException: / by zero";
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception e) {
        ModelAndView modelAndView;
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String requestType = request.getHeader("X-Requested-With");
        boolean ifAjax = "XMLHttpRequest".equals(requestType);
        if (ifAjax) {
            modelAndView = new ModelAndView("jsonConverter", ResultVOUtils.message(30, e.getMessage()).toMap());
        } else {
            modelAndView = new ModelAndView("system/error", ResultVOUtils.message(401, e.getMessage()).toMap());
        }
        return modelAndView;
    }
}
