package com.gree.first.utils;

import freemarker.template.utility.StringUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * session 上下文，创建一个保存session 的容器，用于通过 sessionid 获取 session
 *
 * Create by yang_zzu on 2020/5/25 on 19:10
 */
public class SessionContext {

    private static Map<String, HttpSession> sessionMap;

    //单例模式
    private SessionContext() {
        sessionMap = new ConcurrentHashMap<>();
    }

    private enum SessionContextSingle {
        INSTANCE;

        private SessionContext sessionContext;

        SessionContextSingle() {
            sessionContext = new SessionContext();
        }

        public SessionContext getInstance() {
            return sessionContext;
        }
    }

    public static SessionContext getInstance() {
        return SessionContextSingle.INSTANCE.getInstance();
    }

    /**
     * 添加session
     * @param httpSession
     */
    public synchronized void addSession(HttpSession httpSession) {
        if (httpSession != null) {
            sessionMap.put(httpSession.getId(), httpSession);
        }

    }

    /**
     * 移除session
     * @param httpSession
     */
    public synchronized void deleteSession(HttpSession httpSession) {
        if (httpSession != null) {
            sessionMap.remove(httpSession.getId());
        }

    }

    /**
     * 通过 sessionId 获得 session
     * @param sessionId
     * @return
     */
    public HttpSession getSession(String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            return null;
        } else {
            return sessionMap.get(sessionId);
        }

    }


}
