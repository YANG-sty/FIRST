package com.gree.user.controller;

import com.gree.first.user.dto.UserDto;
import com.gree.first.user.service.UserSecendService;
import com.gree.first.user.service.UserService;
import com.gree.first.utils.ResultVO;
import com.gree.first.utils.ResultVOUtils;
import com.gree.first.utils.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangLongFei 2020-11-27-15:19
 */
@RestController
public class LoginController {

    @Autowired
    private UserSecendService userSecendService;
    @Autowired
    private UserService userService;

    /**
     * http://192.168.44.10:8081/greeFIRST/secendlogin?uslg=root&passwd=123
     */
    @RequestMapping("/secendlogin")
    public ResultVO secendlogin(String uslg, String passwd, HttpSession httpSession) {
        //查询用户
        UserDto userDto = userSecendService.queryByName(uslg);
        if (userDto != null) {
            boolean equals = userDto.getUserPassword().equals(passwd);
            if (equals) {
                Map<String, String> map = new HashMap<>();
                httpSession.setAttribute("user", userDto);
                //sessionid 回传
                map.put("sessionid", httpSession.getId());
                //session 放到容器中
                SessionContext.getInstance().addSession(httpSession);
                return ResultVOUtils.successData(map);
            } else {
                return ResultVOUtils.message(401, "密码错误");
            }
        }
        return ResultVOUtils.message(401, "用户不存在");

    }

    /**
     * http://localhost:8081/greeFIRST/masterlogin?uslg=root&passwd=0
     */
    @RequestMapping("/masterlogin")
    public ResultVO masterlogin(String uslg, String passwd, HttpSession httpSession) {
        //查询用户
        UserDto userDto = userService.queryByName(uslg);
        if (userDto != null) {
            boolean equals = userDto.getUserPassword().equals(passwd);
            if (equals) {
                Map<String, String> map = new HashMap<>();
                httpSession.setAttribute("user", userDto);
                //sessionid 回传
                map.put("sessionid", httpSession.getId());
                //session 放到容器中
                SessionContext.getInstance().addSession(httpSession);
                return ResultVOUtils.successData(map);
            } else {
                return ResultVOUtils.message(401, "密码错误");
            }
        }
        return ResultVOUtils.message(401, "用户不存在");

    }
}
