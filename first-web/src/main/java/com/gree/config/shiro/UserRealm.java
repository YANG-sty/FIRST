package com.gree.config.shiro;

import com.gree.beans.CommonUserDto;
import com.gree.first.user.dto.UserDto;
import com.gree.first.user.service.UserService;
import com.gree.first.utils.SpringContextHolder;
import com.gree.first.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author yangLongFei 2020-11-21-16:01
 */
public class UserRealm extends AuthorizingRealm {

    /**
     * subject 用户
     * securityManager 管理所有用户
     * realm 连接数据
     */

    /**
     * 授权 Author
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println( " 执行 =》 author ...... ");
        return getAuthorizationInfo();
    }

    /**
     * 认证 Authen
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println( " 执行 =？  authen .......");
        //获取当前用户
        Subject subject = ShiroUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = null;
        if (authenticationToken instanceof UsernamePasswordToken) {
            usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        } else {
            return null;
        }

        //拿到 用户名，密码
        String username = usernamePasswordToken.getUsername();
        char[] chars = usernamePasswordToken.getPassword();
        String pass = String.valueOf(chars);

        //todo 到数据库中进行验证
        UserService bean = SpringContextHolder.getBean(UserService.class);
        UserDto user = bean.queryByName(username);
        if (user == null) {
//            throw new UnknownAccountException("用户不存在 UnknownAccountException。。。。。。。。");
            return null;
        }

        CommonUserDto commonUserDto = new CommonUserDto();
        BeanUtils.copyProperties(user, commonUserDto);

        //踢出其位置的登录人
        kickOutSeesion(username);

        /**
         * shiro 框架进行 密码认证， shiro- 会进行加密，也可以自己进行加密处理
         * 传递的第一参数 user , 之后可以使用 SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal() 获得该数据
         */
        SimpleAuthenticationInfo userDTO = new SimpleAuthenticationInfo(commonUserDto, user.getUserPassword(), "");
        return userDTO;
    }

    /**
     * 保证该账号只能在一个浏览器上进行使用
     * 清除其他位置登录的 session
     */
    static void kickOutSeesion(String username) {
        SecurityManager securityManager = SecurityUtils.getSecurityManager();
        DefaultWebSecurityManager defaultWebSecurityManager = (DefaultWebSecurityManager) securityManager;
        DefaultWebSessionManager defaultWebSessionManager = (DefaultWebSessionManager) defaultWebSecurityManager.getSessionManager();
        //获取当前已经登录的用户 session 列表
        Collection<Session> activeSessions = defaultWebSessionManager.getSessionDAO().getActiveSessions();
        for (Session activeSession : activeSessions) {
            //清除当前已经登录的用户 session 列表
            PrincipalCollection principalCollection = (PrincipalCollection) activeSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (principalCollection != null) {
                CommonUserDto userDto = (CommonUserDto) principalCollection.getPrimaryPrincipal();
                if (username.equals(userDto.getUserName())) {
                    defaultWebSessionManager.getSessionDAO().delete(activeSession);
                }
            }
        }
    }

    /**
     * 资源授权
     */
    static AuthorizationInfo getAuthorizationInfo() {
        //获得 认证的时候，存储到 shiro 中的用户
        CommonUserDto user = ShiroUtils.getUser();

        //todo 去数据库中查询数据
        String[] split = user.getResource().split(",");
        Set<String> permissionByRoleIds = new HashSet<>();
        if (split.length > 0) {
            permissionByRoleIds = new HashSet<>(Arrays.asList(split));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // setStringPermissions 设置的权限为 set 集合
        info.setStringPermissions(permissionByRoleIds);
        //addStringPermission 设置权限为 String 字符串
//        info.addStringPermission("add:update");

        info.setRoles(user.getRolesName());

        return info;
    }



    /*************************************************************/



}
