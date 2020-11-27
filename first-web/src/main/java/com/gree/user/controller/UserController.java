package com.gree.user.controller;

import com.gree.beans.CommonUserDto;
import com.gree.first.user.dto.UserDto;
import com.gree.first.user.service.UserService;
import com.gree.first.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yangLongFei 2020-11-21-16:24
 */
@Controller
@Slf4j
public class UserController {

    /**
     * 在请求的时候，参数的名称必须为 sessionid, 否则切点无法正常的获得 session 中的数据
     */
    @GetMapping("/main")
    public ModelAndView main(String sessionid, ModelAndView modelAndView) {
        modelAndView.setViewName("firstgree/main");
        return modelAndView;
    }

    @RequestMapping({"/", "/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg", "hello shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add() {
        System.out.println("add................");
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update() {
        System.out.println("update ...... ");
        return "user/update";
    }

    /**
     * 用户过期登录，跳转页面
     */
    @GetMapping("/toLogin")
    public String toLogin() {
        if (ShiroUtils.isAuthenticated() || ShiroUtils.getUser() != null) {
            return "redirect:/";
        }
        return "login";
    }


    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        //获得当前用户
        Subject subject = ShiroUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

        try {
            //执行登录的方法
            subject.login(usernamePasswordToken);
            Subject currentSubject = ShiroUtils.getSubject();
            Session session = currentSubject.getSession();
            CommonUserDto user = ShiroUtils.getUser();
            session.setAttribute("loginUser", user);

        } catch (UnknownAccountException uae) {
            log.info("There is no user with username of " + usernamePasswordToken.getPrincipal());
            model.addAttribute("msg", "用户不存在");
            return "login";
        } catch (IncorrectCredentialsException ice) {
            log.info("Password for account " + usernamePasswordToken.getPrincipal() + " was incorrect!");
            model.addAttribute("msg", "密码错误");
            return "login";
        } catch (LockedAccountException lae) {
            log.info("The account for username " + usernamePasswordToken.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
            model.addAttribute("msg", "用户已经被锁住，稍后尝试");
            return "login";
        }
        // ... catch more exceptions here (maybe custom ones specific to your application?
        catch (AuthenticationException ae) {
            //unexpected condition?  error?
        }

        return "index";
    }


    /**
     * 未授权
     */
    @RequestMapping("/unAuthorized")
    @ResponseBody
    public String unAuthorized() {
        return "未授权，无法进行访问。。。。。";
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserByName")
    public String getUserByName(String userName, Model model) {
        UserDto userDto = userService.queryByName(userName);
        System.out.println("--------------------> " + userDto.toString());
        model.addAttribute("msg", userDto.toString());
        return "index";
    }

}
