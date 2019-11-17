package org.openmore.common.utils;

import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.cms.entity.enums.Platform;
import org.springframework.util.StringUtils;

/**
 * @program: bnibtWebSite
 * @description: 请求头agent解析, 参考：http://tools.jb51.net/table/useragent
 * @author: lz
 * @create: 2019-03-07 15:37
 **/
public class UserAgentUtils {

    /**
     * 是否来自微信
     */
    private static boolean isWechat(String userAgent) {
        if (null != userAgent && userAgent.toLowerCase().contains("micromessenger")) {
            return true;
        }
        return false;
    }

    /**
     * 是否来自移动端
     * * android : 所有android设备
     * * mac os : iphone ipad
     * * windows phone:Nokia等windows系统的手机
     */
    private static boolean isMobile(String userAgent) {
        String[] deviceArray = new String[]{"iphone", "ipod", "ipad", "android", "mobile", "touchpad", "ucweb", "windows phone"};
        for (int i = 0; i < deviceArray.length; i++) {
            if (null != userAgent && userAgent.toLowerCase().contains(deviceArray[i])) {
                return true;
            }
        }
        return false;
    }
    /**
     * 是否来自移动端
     * * android : 所有android设备
     * * mac os : iphone ipad
     * * windows phone:Nokia等windows系统的手机
     */
    private static boolean isAndroid(String userAgent) {
//        String[] deviceArray = new String[]{"android"};
//        for (int i = 0; i < deviceArray.length; i++) {
//            if (null != userAgent && userAgent.toLowerCase().contains(deviceArray[i])) {
//                return true;
//            }
//        }
//        return false;
        return userAgent.toLowerCase().startsWith("android");
    }

    /**
     * 是否来自移动端
     * * android : 所有android设备
     * * mac os : iphone ipad
     * * windows phone:Nokia等windows系统的手机
     */
    private static boolean isAndroidPhone(String userAgent) {
        String[] uaArr = userAgent.split(";");
        if(uaArr.length > 3) {
            return uaArr[0].toLowerCase().equals("android") && uaArr[2].toLowerCase().equals("phone");
        }
        return false;
    }

    /**
     * 是否来自移动端
     * * android : 所有android设备
     * * mac os : iphone ipad
     * * windows phone:Nokia等windows系统的手机
     */
    private static boolean isAndroidPad(String userAgent) {
        String[] uaArr = userAgent.split(";");
        if(uaArr.length > 3) {
            return uaArr[0].toLowerCase().equals("android") && uaArr[2].toLowerCase().equals("pad");
        }
        return false;
    }

    /**
     * 是否来自移动端
     * * android : 所有android设备
     * * mac os : iphone ipad
     * * windows phone:Nokia等windows系统的手机
     */
    private static boolean isIphone(String userAgent) {
        String[] uaArr = userAgent.split(";");
        if(uaArr.length > 3) {
            return uaArr[0].toLowerCase().equals("ios") && uaArr[2].toLowerCase().equals("phone");
        }
        return false;
    }

    /**
     * 是否来自移动端
     * * android : 所有android设备
     * * mac os : iphone ipad
     * * windows phone:Nokia等windows系统的手机
     */
    private static boolean isIpad(String userAgent) {
        String[] uaArr = userAgent.split(";");
        if(uaArr.length > 3) {
            return uaArr[0].toLowerCase().equals("ios") && uaArr[2].toLowerCase().equals("pad");
        }
        return false;
    }

    /**
     * 解析请求来源
     */
    public static Platform parseFromUserAgent(String userAgent) {
        if (StringUtils.isEmpty(userAgent)) {
            return null;
        }
        if(!StringUtils.isEmpty(ThreadLocalConfig.getScope()) && ThreadLocalConfig.getScope().toLowerCase().equals("STATELESS-WEB")) {
            if(isAndroidPhone(userAgent)){
                return Platform.ANDROID;
            } else if(isIphone(userAgent)){
                return Platform.IPHONE;
            } else if(isIpad(userAgent)){
                return Platform.IPAD;
            } else if(isAndroidPad(userAgent)){
                return Platform.ANDROIDPAD;
            }
            return Platform.OTHERS;
        } else {
            if (isWechat(userAgent)) {
                return Platform.MINIAPP;
            }
        }
        return Platform.OTHERS;
    }
}
