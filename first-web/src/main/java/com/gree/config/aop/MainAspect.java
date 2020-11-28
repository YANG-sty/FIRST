package com.gree.config.aop;

import com.gree.first.user.dto.UserDto;
import com.gree.first.utils.HttpContextUtils;
import com.gree.first.utils.SessionContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * controller main方法处理
 * session 传递
 *
 * @author yangLongFei 2020-11-27-17:08
 */
@Aspect
@Component
public class MainAspect {

    /**
     * 主要是 切 public ModelAndView main(String sid, ModelAndView modelAndView)
     * 第一个*号：表示返回类型， *号表示所有的类型。
     * 第二个*号：表示类名，*号表示任意的类名称
     * 第三个*号：表示类名，*号表示任意的类名称
     * 第四个*号：表示任意的参数类型
     *
     * *(..): *号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数
     */
    @Pointcut(value = "execution(* com.gree.*.controller.*.main(String, *))")
    private void cutPoint() {

    }

    @Before("cutPoint()")
    public void mainAspect(JoinPoint joinPoint) {
        String sessionId = (String) joinPoint.getArgs()[0];
        //获取上下文中的
        HttpSession httpSession = HttpContextUtils.getHttpSession();
        //通过 sessionid 获得 session 的对象
        UserDto user = (UserDto) SessionContext.getInstance().getSession(sessionId).getAttribute("user");
        httpSession.setAttribute("user", user);
        //传值到前台
        ModelAndView modelAndView = (ModelAndView) joinPoint.getArgs()[1];
        Map<String, Object> map = new HashMap<>();

        //todo 添加一些其他的数据,返回给前端
        map.put("name", "root");


        modelAndView.addAllObjects(map);

    }

}
