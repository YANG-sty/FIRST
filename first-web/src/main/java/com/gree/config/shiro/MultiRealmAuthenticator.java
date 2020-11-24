package com.gree.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;
import java.util.Iterator;

/**
 * 自定义模块化认证器
 *
 * @author yangLongFei 2020-11-23-16:18
 */
@Slf4j
public class MultiRealmAuthenticator extends ModularRealmAuthenticator {

    /**
     * 重写 doMultiRealmAuthentication
     * 下面的代码，可以在 ModularRealmAuthenticator 的类中，复制过来然后进行修改
     */
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {

        AuthenticationStrategy strategy = this.getAuthenticationStrategy();
        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);
        if (log.isTraceEnabled()) {
            log.trace("Iterating through {} realms for PAM authentication", realms.size());
        }

        Iterator i$ = realms.iterator();

        AuthenticationException authenticationException = null;
        while (i$.hasNext()) {
            Realm realm = (Realm) i$.next();
            aggregate = strategy.beforeAttempt(realm, token, aggregate);
            if (realm.supports(token)) {
                log.trace("Attempting to authenticate token [{}] using realm [{}]", token, realm);
                AuthenticationInfo info = null;
                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (AuthenticationException var11) {
                    authenticationException = var11;
                    if (log.isDebugEnabled()) {
                        String msg = "Realm [" + realm + "] threw an exception during a multi-realm authentication attempt:";
                        log.debug(msg, var11);
                    }
                }

                aggregate = strategy.afterAttempt(realm, token, info, aggregate, authenticationException);
            } else {
                log.debug("Realm [{}] does not support token {}.  Skipping realm.", realm, token);
            }
        }

        /**
         * 在循环完后简单的判断了下是否存在异常。如果存在异常，则抛出。
         */
        if(authenticationException != null){
            throw authenticationException;
        }

        aggregate = strategy.afterAllAttempts(token, aggregate);
        return aggregate;
    }
}
