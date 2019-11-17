package org.openmore.cms.filter;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 控制并发登录人数
 * 控制说明：
 * 1、用户未登陆时保存用户访问的设备token
 * 2、已登陆成功用户再次发起服务请求时将检查是否重复设备登陆，若重复将踢出之前用户
 * 3、踢出规则：a、被管理员清除（deviceToken）踢出  b、重复设备（deviceToken）c、单一类型设备登陆（浏览器、微信、app，各类型设备同时只能有一台登陆）【暂未实现】
 */
public class ShiroSessioinFilter extends AccessControlFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //踢出后到的地址
    private String kickoutUrl;

    //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private boolean kickoutAfter = false;

    //同一个帐号最大会话数 默认（暂时未使用）
    private int maxSession = 3;

    private SessionManager sessionManager;

    private Cache<String, LinkedList<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

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
//        HeaderReadHelper readHelper = new HeaderReadHelper();
//        //return super.preHandle(request, response);
//        Subject subject = getSubject(request, response);
//        if (!subject.isAuthenticated() && !subject.isRemembered()) {
//            readHelper.read(request);
//        }
//        logger.debug("==>session deviceToken:" + subject.getSession().getAttribute("deviceToken"));
//        return onAccessDenied(request, response);
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    /**
     * 踢人
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        logger.debug("==>onAccessDenied");
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated()) {
            //如果没有登录，直接进行之后的流程
            logger.debug("==>not authenticated");
            return true;
        }
//        //获取当前用户session
//        TalkSession session = subject.getSession();
//        String userId = (String) subject.getPrincipal();
//        Serializable sessionId = session.getId();
//
//        //同步控制
//        LinkedList<Serializable> deque = cache.get(userId);
//        if (deque == null) {
//            deque = new LinkedList<Serializable>();
//            cache.put(userId, deque);
//        }
//
//        adminkickOut(deque, session);
//
//        //如果该用户被踢出了，直接退出，重定向到踢出后的地址
//        if (session.getAttribute("kickout") != null) {
//            //会话被踢出了
//            try {
//                subject.logout();
//                //从队列中删除该用户session
//                deque.remove(session.getId());
//            } catch (Exception e) {
//            }
//            cache.put(userId, deque);
//            saveRequest(request);
//            responseData(response, "请重新登陆");
//            return false;
//        }
        return true;
    }

    /**
     * 管理员踢出非法用户
     */
    public void adminkickOut(LinkedList<Serializable> deque, Session session) {
        logger.debug("==>开始踢人。。。");
        if (null == deque || deque.size() <= 0 || null != session.getAttribute("kickout")) {
            return;
        }
        Iterator<Serializable> it = deque.iterator();
        while (it.hasNext()) {
            String sessionId = (String) it.next();
            Session oldSession = null;
            try {
                oldSession = sessionManager.getSession(new DefaultSessionKey(sessionId));
                if (null != oldSession.getAttribute("kickout")) {
                    String deviceToken = (String) oldSession.getAttribute("deviceToken");
                    //TODO : 查询数据库该用户登陆此设备号是否被清空，若被清空则踢出
                    //oldSession.setAttribute("kickout", true);//踢出用户登陆
                }
            } catch (UnknownSessionException e) {
                //从列表清除
                it.remove();
            }
        }
    }
}