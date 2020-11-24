package com.gree.first.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gree.first.user.domain.User;

/**
 * Create by yang_zzu on 2020/4/14 on 21:21
 */
public interface UserMapper extends BaseMapper<User> {
    User queryByName(String name);
}
