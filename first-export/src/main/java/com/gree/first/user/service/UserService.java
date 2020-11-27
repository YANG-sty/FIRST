package com.gree.first.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.gree.first.user.domain.User;
import com.gree.first.user.dto.UserDto;

import java.util.Set;

/**
 * @author yangLongFei 2020-11-22-1:21
 */
public interface UserService extends IService<User> {

    /**
     * 添加用户
     */
    boolean addUser(UserDto user);

    /**
     * 查询用户
     */
    UserDto queryByName(String name);

    /**
     * 获得角色对应的资源
     */
    Set<String> getPermissionByRoleIds(Set<Long> roles);


}
