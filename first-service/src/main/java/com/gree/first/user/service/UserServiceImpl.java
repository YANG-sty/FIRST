package com.gree.first.user.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gree.first.user.dao.UserMapper;
import com.gree.first.user.domain.User;
import com.gree.first.user.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yangLongFei 2020-11-22-10:14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean addUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return this.insert(user);
//        return userManager.addUser(user);
    }

    @Override
    public UserDto queryByName(String name) {
        EntityWrapper<User> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", name);
//        User user = this.selectOne(entityWrapper);
        User user = this.baseMapper.queryByName(name);
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    /**
     * 获得角色对应的资源
     *
     * @param roles
     */
    @Override
    public Set<String> getPermissionByRoleIds(Set<Long> roles) {
        Set<String> stringSet = new HashSet<>();
        for (Long role : roles) {
            stringSet.add(String.valueOf(role));
        }
        return stringSet;
    }
}