package org.openmore.cms.controller.web;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.cms.dto.MenuDto;
import org.openmore.cms.entity.User;
import org.openmore.cms.entity.enums.Language;
import org.openmore.cms.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by michaeltang on 2019/1/17.
 */
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected CacheService cacheService;
    @Autowired
    protected DictionaryService dictionaryService;
    /**
     * 缓存Key
     */
    //--------- 缓存时间（秒） ---------
    protected static final long CACHE_EXPIRE = 10;

    //--------- 共通 ---------
    protected static final String KEY_MENUS = "index_menus";
    protected static final String KEY_PRODUCT = "index_product";

    //--------- 首页key
    protected static final String KEY_HOT_PRODUCT = "index_hot_product";
    protected static final String KEY_BANNER = "index_banner";
    protected static final String KEY_NEWS = "index_news";
    protected static final String KEY_FRIEND_LINK = "index_friend_link";


    protected  User getUser () {
        try {
            // 获得登录信息，userId，token
            Subject subject = SecurityUtils.getSubject();
            // 获得本期请求有没有登录信息，用户信息
             User user = ThreadLocalConfig.getUser();
            //如果用户已经登录，获得登录信息，保存起来
            if (subject.isAuthenticated()) {
                String userId = (String) subject.getPrincipal();
                if (user != null && user.getId().equals(userId)) {
                    return user;
                }
                String userString = (String) subject.getSession().getAttribute(userId);
                user = JSONObject.parseObject(userString, User.class);
                ;
                if (null != user) {
                    ThreadLocalConfig.setUser(user);
                }
                return user;
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 如果直接获得SuperUser，则请求中有登录Token，返回0
     * 如果当前请求中没有OpenId，则需要拉授权，返回-1
     * 如果没有绑定用户，则需要绑定手机号，返回1
     * 如果存在该OpenId用户，则执行wechat登录，并返回0
     */
    protected  User checkLogin (HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
        User user = getUser();
        if (user != null) {
            return user;
        }
        String openId = request.getParameter("openId");
        if (StringUtils.isEmpty(openId)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie: cookies) {
                    if (cookie.getName().equals("OpenId")) {
                        openId = cookie.getValue();
                    }
                }
                if (StringUtils.isEmpty(openId)) {
                    view.addObject("errorStatus", "grant");
                    return null;
                }
            } else {
                view.addObject("errorStatus", "grant");
                return null;
            }
        }
        Cookie OpenCookie = new Cookie("OpenId", openId);
        OpenCookie.setPath("/");
        response.addCookie(OpenCookie);
//        WechatUser wechatUser = wechatUserService.getByOpenid(openId);
//        if (wechatUser != null && StringUtils.isEmpty(wechatUser.getStaffId()) && StringUtils.isEmpty(wechatUser.getUserId())) {
//            view.addObject("errorStatus", "bindMobile");
//            return null;
//        }
//        UserProfileDto dto = accountService.phoneBind(openId, null, null);
//        if (dto != null) {
//            Cookie userCookie = new Cookie("userId", dto.getId());
//            userCookie.setPath("/");
//            response.addCookie(userCookie);
//        }
        return getUser();
    }

    protected Language getLocalLanguage(HttpServletRequest requesst, HttpServletResponse response) {
        // 语言处理
        Language lan = Language.ZH;
        String language = requesst.getParameter("language");
        if (!StringUtils.isEmpty(language) && language.equals("en")) {
            lan = Language.EN;
        }
        Cookie lanCookie = new Cookie("language", lan.toString().toLowerCase());
        lanCookie.setPath("/");
        response.addCookie(lanCookie);
        logger.debug("当前语言为：" + lan);
        return lan;
    }

    /**
     * 获得产品目录树，按照排序
     *
     * @return
     */
    protected List<MenuDto> getProductMenu() {
        // 获得产品类型词典
        List<MenuDto> productMenuList = cacheService.get(KEY_MENUS, MenuDto.class);
        if (productMenuList == null) {
            productMenuList = dictionaryService.getMenuTreeByParentId("PRODUCT_TYPE", true);
            cacheService.set(KEY_MENUS, productMenuList, CACHE_EXPIRE);
        }
        return productMenuList;
    }

    /**
     * 获得本地化语言的产品目录树
     *
     * @return
     */
    protected List<Map<String, Object>> getLocalProductMenu(Language lan) {
        String lanPath = null;
        if (lan == Language.EN) {
            lanPath = "/en/";
        } else {
            lanPath = "/";
        }
        // 获得产品类型词典
        List<Map<String, Object>> menus = new ArrayList<>();
        for (MenuDto item : getProductMenu()) {
            if (item.getChildren() == null || item.getChildren().size() == 0) {
                continue;
            }
            Map<String, Object> map = new HashedMap();
            map.put("en", item.getEnName());
            map.put("zh", item.getName());
            map.put("url", lanPath + "products.html?type=" + item.getDicKey());
            map.put("id", item.getDicKey());
            if (item.getChildren() != null && item.getChildren().size() > 0) {
                List<Map<String, Object>> subList = new ArrayList<>();
                for (MenuDto subMenu : item.getChildren()) {
                    Map<String, Object> subMap = new HashedMap();
                    subMap.put("en", subMenu.getEnName());
                    subMap.put("zh", subMenu.getName());
                    subMap.put("id", subMenu.getDicKey());
                    subMap.put("url", lanPath + "products.html?type=" + subMenu.getDicKey());
                    subList.add(subMap);
                }
                map.put("menus", subList);
            }
            menus.add(map);
        }
        return menus;
    }
}
