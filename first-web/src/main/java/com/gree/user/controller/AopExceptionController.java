package com.gree.user.controller;

import com.gree.first.utils.ResultVOUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * GlobalExceptionHandler
 * 使用的时候，业务逻辑的接口方法上面不用准确指定是哪种的异常就可以
 * @author yangLongFei 2021-03-29-16:47
 */
@Controller
@RequestMapping("/api/test")
public class AopExceptionController {

    @GetMapping("/initBinderAop")
    public ModelAndView initBinderAop(@RequestParam("id") String id, Date date) {
        String rs = id + " # " + date;
        return new ModelAndView("index", ResultVOUtils.message(200, rs).toMap());
    }

    @GetMapping("/aopException")
    public ModelAndView aopException() {
        int i = 1 / 0;
        return new ModelAndView("system/helloIndex");
    }
    @PostMapping("/postAopException")
    public ModelAndView postAopException() {
        int i = 1 / 0;
        return new ModelAndView("system/helloIndex");
    }

    @GetMapping("/aopExceptionTest")
    public String aopExceptionTest(@Param("id") String id) throws ArithmeticException{
        int i = 1 / 0;
        return id;
    }

    @GetMapping("/aopExceptionTestException")
    public String aopExceptionTestException(@Param("id") String id) throws Exception{
        int i = 1 / 0;
        return id;
    }

    /**
     * 手动处理过的异常，不会被全局的异常处理器进行捕捉
     * 手动的进行捕获不会被 GlobalExceptionHandler 捕捉到执行里面的逻辑
     * @param id
     * @return
     */
    @GetMapping("/aopExceptionTestTryCatch")
    public String aopExceptionTestTryCatch(@Param("id") String id){
        try {
            int i = 1 / 0;
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        return id;
    }

}
