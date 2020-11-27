package com.gree.config.webconfig;

import com.gree.first.utils.SessionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * httpsession 监听
 * Create by yang_zzu on 2020/5/25 on 19:40
 */
@Component
public class SessionListener implements HttpSessionListener {

    private SessionContext sessionContext = SessionContext.getInstance();


    /**
     * 重写 HttpSessionListener 的方法
     *
     * 将 session 的数据，存储到，全局的 Map<String, HttpSession> sessionMap 的变量里面
     * 在 SessionContext 类里面，有对 sessionMap 的 增，删，查 的操作。
     *
     * 在使用的过程中，
     * 可以通过
     */


    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession httpSession = httpSessionEvent.getSession();
        //服务器存活时间 24h
        httpSession.setMaxInactiveInterval( 24 * 60 * 60);
        sessionContext.addSession(httpSession);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession httpSession = httpSessionEvent.getSession();
        sessionContext.deleteSession(httpSession);

    }
}
