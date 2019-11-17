<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import "common/language.ftl" as Language>
<@base.base_container
title="${Language.lans[lan].coffers_title}"
headers=[
"/css/breadcrumb.css",
"/css/offers.css"
]
scripts=[
"/js/campus-offers.js"
]>
    <img class="banner hidden-xs" src="http://img.bnibt.com/static/banner_sevice.png"
         alt="">
    <img class="banner visible-xs-block"
         src="http://img.bnibt.com/static/banner_sevice.png?x-oss-process=image/crop,w_750,h_300,g_center"
         alt="">
    <div class="offers">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "#",
        "zh": "加入我们",
        "en": "Join Us"
        }, {
        "url": "${Language.lans[lan].url_path}coffers.html",
        "zh": "校园招聘",
        "en": "Offers"
        }] />
        <div class="offers-list">
            <div class="container">
                <div class="list-container">
                    <#if offerList??>
                        <#list offerList as offer>
                            <a class="offers-item" href="${offer.action}" target="_blank">
                                <div class="offers-item-left">
                                    <h2 class="offers-item-name">${offer.title}</h2>
                                    <p class="offers-item-time">${offer.updatedTime?string("yyyy-MM-dd")}</p>
                                </div>
                                <div class="offers-item-right">
                                    <img src="http://img.bnibt.com/static/r_black.png"
                                         alt="">
                                </div>
                            </a>
                        </#list>
                    <#else>
                        <div class="offers-item-none">
                            <img src="http://img.bnibt.com/static/img_none.png"
                                 alt="">
                            <p>${Language.lans[lan].products_no_data}</p>
                        </div>
                    </#if>
                </div>

                <div class="offers-message-desktop hidden-xs">
                    <img src="http://img.bnibt.com/static/zhaopin_banner.png" alt="">
                </div>

                <#if page??>
                    <div class="offers-pages hidden-xs">
                        <nav aria-label="Page navigation">
                            <ul class="pagination"
                                content="${page.page?c}/${page.totalPage?c}/${page.limit?c}/${page.totalItem?c}">
                                <div class="page-total">
                                    <p>${page.totalItem?c}条数据，共${page.totalPage?c}页</p>
                                </div>
                                <li class="previous-page">
                            <span aria-label="Previous">
                                上一页
                            </span>
                                </li>
                                <#if page.totalItem != 0>
                                    <#list 1..page.totalPage as item>
                                        <li class="${(page.page == item)?string("active", "")} pageIndex">
                                            <a role="button" href="#">${item?c}</a>
                                        </li>
                                    </#list>
                                </#if>
                                <li class="next-page ${(page.page == page.totalPage)?string("disabled", "")}">
                                    <a href="#" aria-label="Next">
                                        下一页
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </#if>
            </div>
        </div>
        <#if page??>
            <div class="mobile-more offers-more visible-xs-block">
                <a href="#" class="btn previous">上一页</a>
                <a href="#" class="btn next">下一页</a>
            </div>
        </#if>
    </div>
</@base.base_container>