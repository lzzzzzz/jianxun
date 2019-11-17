package org.openmore.cms.filter;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * 退出登录过滤器-用于退出登录后清除缓存
 * 控制说明：
 * 1、用户退出登录清理缓存表
 */
public class ShiroLogoutFilter extends AccessControlFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //同一个帐号最大会话数 默认（暂时未使用）
    private int maxSession = 3;

    private SessionManager sessionManager;

    private Cache<String, LinkedList<Serializable>> cache;

    private String logoutSessionId;

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return true;
    }

    /**
     * 踢人
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return true;
    }

    /**登录成功后检查踢人*/
    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        super.afterCompletion(request, response, exception);
        //return super.onLoginSuccess(token, subject, request, response);
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated()) {
            //若用户退出登录
            //如果没有登录，直接进行之后的流程
            logger.debug("==>用户退出登录");
            //获取当前用户session
            Session session = subject.getSession();
            String userId = (String) subject.getPrincipal();
            Serializable sessionId = session.getId();

            //同步控制
            LinkedList<Serializable> deque = cache.get(userId);
            logger.debug("==>deque.size:" + deque.size());
            if (null != deque && deque.size() > 0 && deque.contains(sessionId)){
                deque.remove(sessionId);
                cache.put(userId, deque);
            }
            logger.debug("==>deque.size:" + deque.size());
            return;
        }
    }
}