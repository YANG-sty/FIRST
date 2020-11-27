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

    //主要是 切 public ModelAndView main(String sid, ModelAndView modelAndView)
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

        //添加一些其他的数据
        map.put("name", "root");
        modelAndView.addAllObjects(map);

    }

}
