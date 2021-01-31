package com.gree.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.gree.first.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AbstractAuthenticator;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.bson.internal.Base64;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * @author yangLongFei 2020-11-21-16:03
 */
@Configuration
public class ShiroConfig {

    /**
     * subject 用户
     * securityManager 管理所有用户
     * realm 连接数据
     */

    // ShiroFilterFactoryBean
//    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        /**
         * 使用自定义过滤器，则 realm 中的 授权 AuthorizationInfo 无法进行执行
         * 设置安全管理器, 默认拦截器，无法解决 session 超时的问题
         */
        /*HashMap<String, Filter> myFilter = new HashMap<>();
        myFilter.put("user", new LoginFilter());
        shiroFilterFactoryBean.setFilters(myFilter);*/

        /**
         * 添加 shiro 的内置过滤器
         * anon: 无需认证就可以访问
         * authc: 必须认证了才能访问
         * user: 必须拥有， 记住我 功能才能访问
         * perms: 拥有对某个资源的权限才能访问
         * role: 拥有某个角色权限才能访问
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //静态资源不进行拦截
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/faviscon.ico", "anon");
        filterChainDefinitionMap.put("/couldNotIE", "anon");
        filterChainDefinitionMap.put("/downloadFirefox", "anon");

        //授权, 未授权===> user 用户才能访问 add 资源
        filterChainDefinitionMap.put("/user/add", "perms[user:add]");
        filterChainDefinitionMap.put("/user/update", "perms[user:update]");

//        filterChainDefinitionMap.put("/user/*", "authc");

        //自定义过滤器, myFilter.put("user", new LoginFilter());
//        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //默认会查找根目录下的 /login.jsp 页面，手动设置，没有登录则访问 toLogin 接口
        shiroFilterFactoryBean.setLoginUrl("/toLogin");

        // 资源或者页面未授权, 访问 unAuthorized 接口
        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuthorized");

        return shiroFilterFactoryBean;
    }

    // DefualtWebSecurityManager
//    @Bean(name="defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联 userRealm
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    // 创建 realm 对象, 自定义类
//    @Bean(name="userRealm") // name 的默认值是 方法名称
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 整和 ShiroDialect ; 用来整和 shiro thymeleaf
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

    /********************************************************************************/

    /**
     * 1
     * 记住密码 cookie
     * cookie设置
     * */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // 默认为 true， 防止XSS攻击，窃取cookie内容
        simpleCookie.setHttpOnly(true);
        //cookie生效时间 7 天,单位秒;
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);
        return simpleCookie;
    }

    /**
     * 2
     * rememberMe 管理器
     * cookie管理对象;记住我功能
     */
    @Bean
    public CookieRememberMeManager rememberMeManager( SimpleCookie rememberMeCookie){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie);
        // cookieRememberMeManager.setCipherKey用来设置加密的Key,参数类型byte[],字节数组长度要求16
        cookieRememberMeManager.setCipherKey(Base64.decode("yang_zzuAAAAAAAAAAAAAAA=="));
        return cookieRememberMeManager;
    }

    /**
     * 3
     * 缓存管理器 使用 Ehcache 实现
     */
    @Bean
    public CacheManager cacheManager(@NotNull EhCacheManagerFactoryBean ehcache) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehcache.getObject());
        return ehCacheManager;
    }

    /**
     * 4
     * 会话管理器
     */
    @Bean
    public SessionManager sessionManager(CacheManager cacheManager) {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        //装配缓存管理器
        defaultWebSessionManager.setCacheManager(cacheManager);
        //全局会话超时时间 6H
        defaultWebSessionManager.setGlobalSessionTimeout(6 * 60 * 60 * 1000L);
        //删除无用 session 对象, 默认为 true
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        //定时检测过期的 session, 默认为 true
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        /**
         * 设置 session 失效的扫描时间，清理用户直接关闭浏览器造成的鼓励会话。默认为 1h
         * 设置该属性， 就不需设置 ExecutorServiceSessionValidationScheduler 底层也是调用 ExecutorServiceSessionValidationScheduler
         */
        //defaultWebSessionManager.setSessionValidationInterval(60 * 1000L);
        //设置 cookie
        defaultWebSessionManager.setSessionIdCookie(sessionIdCookie());
        return defaultWebSessionManager;
    }

    /**
     * 5
     * sessionId cookie
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
       //这个参数是 cookie 的名称
        SimpleCookie shiroCookie = new SimpleCookie("shiroCookie");
        //默认为 true， 防止XSS攻击，窃取cookie内容
        shiroCookie.setHttpOnly(true);
        shiroCookie.setPath("/");
        // 默认为 -1 表示浏览器 关闭时，失效此 cookie
        shiroCookie.setMaxAge(-1);
        return shiroCookie;
    }

    /**
     * 6
     * 自定义模块化认证器， 解决多 realm 抛出异常问题
     */
    @Bean
    public AbstractAuthenticator abstractAuthenticator() {
        ModularRealmAuthenticator authenticator = new MultiRealmAuthenticator();
        // 认证策略
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        // 加入 realms
        List<Realm> realms = new ArrayList<>();
        //todo 添加多 Realm
        realms.add(userRealm());
        authenticator.setRealms(realms);

        return authenticator;
    }

    /**
     * 7
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager(CookieRememberMeManager rememberMeManager, CacheManager cacheManager,
                                           SessionManager sessionManager, AbstractAuthenticator abstractAuthenticator) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager);
        defaultWebSecurityManager.setCacheManager(cacheManager);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        // todo 添加 realms
        List<Realm> realms = new ArrayList<>();
        realms.add(userRealm());
        defaultWebSecurityManager.setRealms(realms);

        //多认证器
        defaultWebSecurityManager.setAuthenticator(abstractAuthenticator);
        return defaultWebSecurityManager;
    }

    /**
     * 8
     * 在方法中注入 securityManager 进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(SecurityManager securityManager) {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(securityManager);
        return methodInvokingFactoryBean;
    }

    /**
     * 9
     * 配置 shiro 拦截器
     */
//    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /**
         * 使用自定义过滤器，则 realm 中的 授权 AuthorizationInfo 无法进行执行
         * 设置安全管理器, 默认拦截器，无法解决 session 超时的问题
         */
        HashMap<String, Filter> myFilter = new HashMap<>();
        myFilter.put("user", new LoginFilter());
        shiroFilterFactoryBean.setFilters(myFilter);

        /**
         * 添加 shiro 的内置过滤器
         * anon: 无需认证就可以访问
         * authc: 必须认证了才能访问
         * user: 必须拥有， 记住我 功能才能访问
         * perms: 拥有对某个资源的权限才能访问
         * role: 拥有某个角色权限才能访问
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //静态资源不进行拦截
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/faviscon.ico", "anon");
        filterChainDefinitionMap.put("/couldNotIE", "anon");
        filterChainDefinitionMap.put("/downloadFirefox", "anon");

        //授权, 未授权===> user 用户才能访问 add 资源
        filterChainDefinitionMap.put("/user/add", "perms[user:add]");
        filterChainDefinitionMap.put("/user/update", "perms[user:update]");

//        filterChainDefinitionMap.put("/user/*", "authc");

        //自定义过滤器, myFilter.put("user", new LoginFilter());
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //默认会查找根目录下的 /login.jsp 页面，手动设置，没有登录则访问 toLogin 接口
        shiroFilterFactoryBean.setLoginUrl("/toLogin");

        // 资源或者页面未授权, 访问 unAuthorized 接口
        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuthorized");

        return shiroFilterFactoryBean;

    }


    /**
     * 0
     * Shiro 生命周期处理器
     * 用于实现了， Initializable 接口的 shiro bean ，初始化时调用 Initializable接口回调
     * （例如： UserRealm 继承了 AuthorizingRealm ， AuthorizingRealm 继承了 AuthenticatingRealm， AuthenticatingRealm 实现了 Initializable 的接口 )
     * 在实现了  DefaultSecurityManager 继承了 SessionsSecurityManager， SessionsSecurityManager 实现了 destroy 接口
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用 shiro 授权注解拦截方式， aop 式方法级权限检索
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
