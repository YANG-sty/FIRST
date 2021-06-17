package com.gree.first.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.gree.first.user.domain.UserSecond;
import com.gree.first.user.dto.UserDto;

/**
 * @author yangLongFei 2020-11-27-15:45
 */
public interface UserSecendService extends IService<UserSecond> {
    /**
     * 查询用户
     */
    UserDto queryByName(String name);

}
