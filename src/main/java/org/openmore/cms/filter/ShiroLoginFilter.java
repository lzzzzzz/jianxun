package org.openmore.cms.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.openmore.common.exception.OpenmoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 登录过滤器-用于登录成功后检查踢人
 * 控制说明：
 * 1、用户登录过滤器，登录成功将用户存入缓存表
 */
public class ShiroLoginFilter extends AccessControlFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //同一个帐号最大会话数 默认（暂时未使用）
    private int maxSession = 3;

    private SessionManager sessionManager;

    private Cache<String, LinkedList<Serializable>> cache;

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
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        logger.debug("isAccessAllowed");
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        logger.debug("onAccessDenied");
        return true;
    }

    /**
     * 登录成功后检查踢人
     */
    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        super.afterCompletion(request, response, exception);
    }
}