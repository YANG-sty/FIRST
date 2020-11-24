package com.gree.context;

import com.gree.first.utils.HttpContextUtils;
import com.gree.first.user.domain.User;

/**
 * 获取用户上下文
 *
 * Create by yang_zzu on 2020/5/26 on 18:50
 */
public class UserContextHolder {

    /**
     * 当前登录用户
     * @return user
     */
    public static User getCurrentUser() {
        Object user = HttpContextUtils.getHttpSession().getAttribute("user");
        return (User) user;
    }
}
