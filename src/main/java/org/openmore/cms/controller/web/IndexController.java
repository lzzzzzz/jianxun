package org.openmore.cms.controller.web;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.openmore.cms.entity.enums.Language;
import org.openmore.cms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by michaeltang on 2019/01/16
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping({"/web/index"})
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
        // 语言处理
        Language lan = this.getLocalLanguage(request, response);
        view.addObject("lan", lan.toString().toLowerCase());

        // 获得产品目录
        view.addObject("menus", getLocalProductMenu(lan));

        // 获得推荐类别及当前类型下的产品
        // 获得产品类型词典
//        List<MenuDto> productMenuList = getProductMenu();
//        List<ProductDto> hotProductList = cacheService.get(KEY_HOT_PRODUCT + lan.toString().toLowerCase(), ProductDto.class);
//        if (hotProductList == null) {
//            PageHelper.startPage(1, 4);
//            hotProductList = productService.selectAll(null, null, null,
//                    ArticleType.PRODUCT.getValue(), null, null, true, true,
//                    lan, null, false, null, null);
//            cacheService.set(KEY_HOT_PRODUCT + lan.toString().toLowerCase(), hotProductList, CACHE_EXPIRE);
//
//        }
//        view.addObject("hotProductList", hotProductList);
//
//        // 获得顶部推广运营Banner图
//        List<PromotionDto> carouselList = cacheService.get(KEY_BANNER + lan.toString().toLowerCase(), PromotionDto.class);
//        if (carouselList == null) {
//            carouselList = promotionService.selectAll(PromotionType.HOME_CAROUSEL, null, null, null, lan, null, null);
//            cacheService.set(KEY_BANNER + lan.toString().toLowerCase(), carouselList, CACHE_EXPIRE);
//        }
//        view.addObject("carousel", carouselList);
//
//        // 获得推荐新闻
//        PageHelper.startPage(1, 6);
//        List<ArticleDto> newsList = cacheService.get(KEY_NEWS + lan.toString().toLowerCase(), ArticleDto.class);
//        if (newsList == null) {
//            newsList = articleService.selectAll(null, null, null,
//                    ArticleType.NEWS.getValue() + "," + ArticleType.CAMPAIGN.getValue(), null, true,
//                    lan, null, false, null, null);
//            cacheService.set(KEY_NEWS + lan.toString().toLowerCase(), newsList, CACHE_EXPIRE);
//        }
//        view.addObject("news", newsList);
//
//        // 获得推荐产品
//        PageHelper.startPage(1, 4);
//        List<ProductDto> productList = cacheService.get(KEY_PRODUCT + lan.toString().toLowerCase(), ProductDto.class);
//        if (productList == null) {
//            productList = productService.selectAll(null, null, null,
//                    ArticleType.PRODUCT.getValue(), null, null, true, null,
//                    lan, null, null, null, null);
//            cacheService.set(KEY_PRODUCT + lan.toString().toLowerCase(), productList, CACHE_EXPIRE);
//        }
//        view.addObject("products", productList);
//        PageHelper.startPage(1, 100000);
//        // 获得推广友情链接
//        List<ArticleDto> linkList = cacheService.get(KEY_FRIEND_LINK + lan.toString().toLowerCase(), ArticleDto.class);
//        if (linkList == null) {
//            linkList = articleService.selectAll(null, null, null,
//                    ArticleType.FRIEND_LINK.getValue(), null, true,
//                    lan, null, false, null, null);
//            cacheService.set(KEY_FRIEND_LINK + lan.toString().toLowerCase(), linkList, CACHE_EXPIRE);
//        }
//        view.addObject("links", linkList);
        // 返回数据
        view.setViewName("index");
        return view;
    }

    @RequestMapping({"/web/profile"})
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {


        // 语言处理
        Language lan = this.getLocalLanguage(request, response);
        view.addObject("lan", lan.toString().toLowerCase());

        // 获得产品目录
        view.addObject("menus", getLocalProductMenu(lan));

        view.setViewName("profile");
        return view;
    }

    @RequestMapping({"/web/cooperation"})
    public ModelAndView cooperation(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
        view.setViewName("cooperation");

        // 语言处理
        Language lan = this.getLocalLanguage(request, response);
        view.addObject("lan", lan.toString().toLowerCase());

        // 获得产品目录
        view.addObject("menus", getLocalProductMenu(lan));

        return view;
    }

    @RequestMapping({"/web/history"})
    public ModelAndView history(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
        view.setViewName("history");

        // 语言处理
        Language lan = this.getLocalLanguage(request, response);
        view.addObject("lan", lan.toString().toLowerCase());

        // 获得产品目录
        view.addObject("menus", getLocalProductMenu(lan));

        return view;
    }

    @RequestMapping({"/web/search"})
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
        view.setViewName("search");

        // 语言处理
        Language lan = this.getLocalLanguage(request, response);
        view.addObject("lan", lan.toString().toLowerCase());

        // 获得产品目录
        view.addObject("menus", getLocalProductMenu(lan));

        String searchData = request.getParameter("data");

        view.addObject("searchData", searchData);
        if (StringUtils.isEmpty(searchData)) {
            return view;
        }
        PageHelper.startPage(1, 10000);
        return view;
    }
}
