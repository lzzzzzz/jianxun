package org.openmore.cms.configuration;

import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.openmore.common.shiro.DefaultModularRealm;
import org.openmore.common.shiro.MyCredentialsMatcher;
import org.openmore.cms.filter.ShiroLoginFilter;
import org.openmore.cms.filter.ShiroLogoutFilter;
import org.openmore.cms.filter.ShiroSessioinFilter;
import org.openmore.common.shiro.UserRealm;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.servlet.Filter;
import java.util.*;

/**
 * Created by michaeltang on 2018/3/22.
 */
@Configuration
@EnableRedisHttpSession
public class ShiroConfiguration {

    private final int HOUR_IN_SEC = 3600;
    private final int DAY_IN_SEC = 24 * HOUR_IN_SEC;
    @Autowired
    private Environment env;

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(new MyCredentialsMatcher());
        userRealm.setAuthenticationCachingEnabled(true);
        return userRealm;
    }

    /**
     * 启用自定义sessionId防止项目冲突
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("JianXun");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7000 * DAY_IN_SEC);
        return cookie;
    }

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        HttpSessionManager mySessionManager = new HttpSessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        mySessionManager.setSessionIdCookieEnabled(true);
        mySessionManager.setGlobalSessionTimeout(7000 * DAY_IN_SEC);
        mySessionManager.setDeleteInvalidSessions(true);
        //启用自定义sessionId防止项目冲突
        mySessionManager.setSessionIdCookie(sessionIdCookie());
        return mySessionManager;
    }

    @Bean
    public DefaultModularRealm modularRealmAuthenticator() {
        DefaultModularRealm myAuthenticator = new DefaultModularRealm();
        myAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return myAuthenticator;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        UserRealm userRealm = userRealm();
        List<Realm> realmList = new ArrayList<>();
        realmList.add(userRealm);
        securityManager.setRealms(realmList);
        DefaultModularRealm defaultModularRealm = modularRealmAuthenticator();
        Map<String, Realm> realmMap = new HashMap<>();
        realmMap.put("userRealm", userRealm);
        defaultModularRealm.setDefinedRealms(realmMap);
        securityManager.setAuthenticator(defaultModularRealm);
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        //添加session过滤器
        filtersMap.put("myAccessControlFilter", shiroSessioinFilter());
        filtersMap.put("loginFilter", shiroLoginFilter());
        filtersMap.put("logoutFilter", shiroLogoutFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //手机号快速登陆
//        filterChainDefinitionMap.put("/api/account/mobileLogin/*", "loginFilter");
        //接口账号密码登陆
        filterChainDefinitionMap.put("/api/account/login", "loginFilter");
        //web账号密码登陆
        filterChainDefinitionMap.put("/web/index", "loginFilter");
        //三方登录
//        filterChainDefinitionMap.put("/api/account/thirdPartyAuthLogin", "loginFilter");
        //接口退出登录
        filterChainDefinitionMap.put("/api/account/logout", "logoutFilter");
        //web退出登录
        filterChainDefinitionMap.put("/web/logout", "logoutFilter");
        filterChainDefinitionMap.put("/**", "myAccessControlFilter");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/api/account/login");
        shiroFilterFactoryBean.setLoginUrl("/cms/login");
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * 配置shiro redisManager
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(env.getProperty("spring.redis.host"));
        redisManager.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
        redisManager.setExpire(7 * DAY_IN_SEC);
        redisManager.setPassword(env.getProperty("spring.redis.password"));
//        redisManager.setTimeout(1800);
//        redisManager.setPassword(env.getProperty("spring.redis.password"));
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

//    @Bean
//    public HttpSessionStrategy httpSessionStrategy() {
//        return new HeaderHttpSessionStrategy();
//    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean(name = "sessionIdGenerator")
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }


    /**
     * 解决不能自动注解的问题
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 此过滤器作用：拦截登录请求，用户登陆后查询登陆是否合法（不合法踢人），并缓存用户sessionId
     * 此处filter不能注解为Bean,否则会被spring接管拦截器，shiro拦截顺序失效
     *
     * @link: https://blog.csdn.net/carllucasyu/article/details/79455760
     */
    public ShiroLoginFilter shiroLoginFilter() {
        ShiroLoginFilter shiroLoginFilter = new ShiroLoginFilter();
        shiroLoginFilter.setCacheManager(cacheManager());
        shiroLoginFilter.setSessionManager(sessionManager());
        return shiroLoginFilter;
    }

    /**
     * 此过滤器作用：拦截退出登录请求，用户退出登陆后清除session缓存
     * 此处filter不能注解为Bean,否则会被spring接管拦截器，shiro拦截顺序失效
     *
     * @link: https://blog.csdn.net/carllucasyu/article/details/79455760
     */
    public ShiroLogoutFilter shiroLogoutFilter() {
        ShiroLogoutFilter shiroLogoutFilter = new ShiroLogoutFilter();
        shiroLogoutFilter.setCacheManager(cacheManager());
        shiroLogoutFilter.setSessionManager(sessionManager());
        return shiroLogoutFilter;
    }

    /**
     * 此过滤器作用：负责拦截所有请求，身份合法（header验证）、权限允许则可以通过，否则踢出（管理员踢人在此处生效）
     * 此处filter不能注解为@Bean,否则会被spring接管拦截器，shiro拦截顺序失效
     *
     * @link: https://blog.csdn.net/carllucasyu/article/details/79455760
     */
    public ShiroSessioinFilter shiroSessioinFilter() {
        ShiroSessioinFilter shiroSessioinFilter = new ShiroSessioinFilter();
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        //这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
        //也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
        shiroSessioinFilter.setCacheManager(cacheManager());
        //用于根据会话ID，获取会话进行踢出操作的；
        shiroSessioinFilter.setSessionManager(sessionManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
        //kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        //kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        // kickoutSessionControlFilter.setKickoutUrl("/kickout");
        return shiroSessioinFilter;
    }
}
