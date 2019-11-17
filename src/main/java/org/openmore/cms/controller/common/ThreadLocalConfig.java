package org.openmore.cms.controller.common;

import org.openmore.cms.entity.User;

/**
 * Created by michaeltang on 2018/8/23.
 */
public class ThreadLocalConfig {
    private static ThreadLocal<String> ipAddrThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> macThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> uaThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> deviceToken = new ThreadLocal<>();
    private static ThreadLocal<String> xScope = new ThreadLocal<>();
    private static ThreadLocal<String> requestHost = new ThreadLocal<>();

    /**
     * 获得线程内全局的范围信息
     * @return
     */
    public static String getScope() {
        return xScope.get();
    }

    /**
     * 设置请求scope
     * @return
     */
    public static void setScope(String scope) {
        xScope.set(scope);
    }


    /**
     * 获得线程内全局的设备信息
     * @return
     */
    public static String getDeviceToken() {
        return deviceToken.get();
    }

    /**
     * 设置请求host
     * @return
     */
    public static void setRequestHost(String host) {
        requestHost.set(host);
    }

    /**
     * 获得线程内全局的请求host
     * @return
     */
    public static String getRequestHost() {
        return requestHost.get();
    }

    /**
     * 设置设备信息
     * @return
     */
    public static void setDeviceToken(String dt) {
        deviceToken.set(dt);
    }

    /**
     * 获得线程内全局的UA信息
     * @return
     */
    public static String getUserAgent() {
        return uaThreadLocal.get();
    }

    /**
     * 设置UA信息
     * @return
     */
    public static void setUserAgent(String ua) {
        uaThreadLocal.set(ua);
    }


    /**
     * 获得线程内全局的ip地址
     * @return
     */
    public static String getIPAddr() {
        return ipAddrThreadLocal.get();
    }
    /**
     * 获得线程内全局的ip地址
     * @return
     */
    public static void setIPAddr(String ipAddress) {
        ipAddrThreadLocal.set(ipAddress);
    }
    /**
     * 获得线程内全局的mac地址
     * @return
     */
    public static void setMACAddr(String mac) {
        macThreadLocal.set(mac);
    }
    /**
     * 获得线程内全局的mac地址
     * @return
     */
    public static String getMACAddr() {
        return macThreadLocal.get();
    }

    /**
     * 获得线程内全局的当前登录人员，如果非登录用户，返回空
     * @return
     */
    public static User getUser() {
        return userThreadLocal.get();
    }

    /**
     * 设置线程内全局的当前登录人员
     */
    public static void setUser(User user) {
        userThreadLocal.set(user);
    }
}
