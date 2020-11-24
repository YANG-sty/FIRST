package com.gree.first.utils;

import com.gree.beans.CommonUserDto;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * @author yangLongFei 2020-11-21-18:52
 */
public class ShiroUtils {

    private static final String NAME_DELIMETER = ",";

    private ShiroUtils() {
    }

    /**
     * 获取当前 Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取封装的 ShiroUser
     */
    public static CommonUserDto getUser() {
        // 不是认证的用户，则返回空
        if (!isUser()) {
            return null;
        } else {
            return (CommonUserDto) getSubject().getPrincipals().getPrimaryPrincipal();
        }
    }

    /**
     * 从 shiro 获得 session
     */
    public static Session getSession() {
        return getSubject().getSession();
    }

    /**
     * 获得 shiro 指定的 session
     *
     * SuppressWarnings 告诉编译器忽略 unchecked 警告信息，如使用List，ArrayList等未进行参数化产生的警告信息。
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttr(String key) {
        Session session = getSession();
        return session != null ? (T) session.getAttribute(key) : null;
    }

    /**
     * 设置 shiro 指定的 sessionKey
     */
    public static void getSeesionAttr(String key, Object value) {
        Session session = getSession();
        session.setAttribute(key, value);

    }


    /**
     * 移除 session 指定的 sessionKey
     */
    public static void removeSessionAttr(String key) {
        Session session = getSession();
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    /**
     * 验证当前用户，是否属于该角色
     */
    public static boolean hasRole(String roleName) {
        return getSubject() != null && StringUtils.isNotEmpty(roleName) && getSubject().hasRole(roleName);
    }


    /**
     * 验证用户 是否属于以下 任意一个角色
     */
    public static boolean hasAnyRoles(String roleNames) {
        boolean hasAnyRole = false;
        Subject subject = getSubject();
        if (subject != null && StringUtils.isNotEmpty(roleNames)) {
            for (String s : roleNames.split(NAME_DELIMETER)) {
                if (subject.hasRole(s.trim())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }
        return hasAnyRole;
    }

    /**
     * 验证用户是否拥有指定权限
     */
    public static boolean hasPermission(String permission) {
        if (!isAuthenticated()) {
            return false;
        } else if (StringUtils.isNotBlank(permission)) {
            return true;
        } else {
            return getSubject().isPermitted(permission);
        }
    }

    /**
     * 判断用户，是否用于多个权限的一个
     */
    public static boolean hasAnyPermission(List<String> permission) {
        if (!isAuthenticated()) {
            return false;
        } else if (permission.isEmpty()) {
            //权限为空，说明资源是公共资源，默认放行
            return true;
        } else {
            return permission.stream().anyMatch(per -> getSubject().isPermitted(per));
        }
    }

    /**
     * 用户是否 认证通过，不包含已记住的用户，这时与 user 标签的区别所在
     */
    public static boolean isAuthenticated() {
        return getSubject() != null && getSubject().isAuthenticated();
    }

    /**
     * 认证通过，或已记住的用户，
     */
    public static boolean isUser() {
        return getSubject() != null && getSubject().getPrincipal() != null;
    }

    /**
     * 输出当前用户信息
     */
    public static String principal() {
        if (getSubject() != null) {
            Object principal = getSubject().getPrincipal();
            return principal.toString();
        }
        return "";
    }







}
