package com.gree.user.controller;

import com.gree.first.user.dto.UserDto;
import com.gree.first.user.service.UserService;
import com.gree.first.utils.ResultVO;
import com.gree.first.utils.ResultVOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yangLongFei 2020-11-27-20:02
 */
@Controller
public class UserCURDController {

    @Autowired
    private UserService userService;

    /**
     * 验证自动填充
     * http://localhost:8080/greeFIRST/userAdd?userName=xiaoming&&userPassword=123
     */
    @RequestMapping("/userAdd")
    @ResponseBody
    public ResultVO userAdd(UserDto userDto) {
        boolean b = userService.addUser(userDto);
        UserDto userDto1 = userService.queryByName(userDto.getUserName());
        return ResultVOUtils.successData(userDto1);
    }

}
