<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import "common/language.ftl" as Language>
<@base.base_container
title="${Language.lans[lan].search_title}"
headers=[
"/css/breadcrumb.css",
"/css/search.css"
]
scripts=[
"/js/search.js"
]>
    <div class="search">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "#",
        "zh": "搜索结果",
        "en": "Search"
        }] />
        <div class="container">
            <h1 class="search-title">
                ${Language.lans[lan].search_result_title}
            </h1>
            <div class="search-data">
                <p>${Language.lans[lan].search_data_label}：${(searchData)!'无'}</p>
            </div>
            <div class="result-container ${
            (resultList?? && resultList?size gt 0)?string("data", "")}">
                <div class="result-list">
                    <#list resultList as item>
                        <#if item.type == "NEWS">
                            <a href="${Language.lans[lan].url_path}news/${item.id}.html">
                                <div class="result-item">
                                    <div class="result-item-title">
                                        <h2><span class="label">${Language.lans[lan].search_type_news}</span>${(item.title)!'无'}</h2>
                                    </div>
                                    <div class="result-item-data">
                                        <#if (item.bannerList[0].resourceUrl)??>
                                            <div class="result-item-banner">
                                                <img src="${(item.bannerList[0].resourceUrl)!''}${(item.bannerList[0].params)!''}" alt="${item.title}">
                                            </div>
                                        </#if>
                                        <div class="result-item-infor">
                                            <p>${(item.brief)!''}</p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        <#elseif item.type == "PRODUCT">
                            <a href="${Language.lans[lan].url_path}products/${item.id}.html">
                                <div class="result-item">
                                    <div class="result-item-title">
                                        <h2><span class="label">${Language.lans[lan].search_type_product}</span>${(item.title)!'无'}</h2>
                                    </div>
                                    <div class="result-item-data">
                                        <#if (item.bannerList[0].resourceUrl)??>
                                            <div class="result-item-banner">
                                                <img src="${(item.bannerList[0].resourceUrl)!''}${(item.bannerList[0].params)!''}" alt="${item.title}">
                                            </div>
                                        </#if>
                                        <div class="result-item-infor">
                                            <p>${(item.brief)!''}</p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        <#elseif item.type == "ARTICLE_TYPE_COMPANY_CLASSE">
                            <a href="${Language.lans[lan].url_path}training/${item.id}.html">
                                <div class="result-item">
                                    <div class="result-item-title">
                                        <h2><span class="label">${Language.lans[lan].search_type_training}</span>${(item.title)!'无'}</h2>
                                    </div>
                                    <div class="result-item-data">
                                        <#if (item.bannerList[0].resourceUrl)??>
                                            <div class="result-item-banner">
                                                <img src="${(item.bannerList[0].resourceUrl)!''}${(item.bannerList[0].params)!''}" alt="${item.title}">
                                            </div>
                                        </#if>
                                        <div class="result-item-infor">
                                            <p>${(item.brief)!''}</p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        <#elseif item.type == "ARTICLE_TYPE_COMPANY_ISSUE">
                            <div class="result-item">
                                <div class="result-item-title">
                                    <h2><span class="label">${Language.lans[lan].search_type_issue}</span>${(item.title)!'无'}</h2>
                                </div>
                                <div class="result-item-data">
                                    <#if (item.bannerList[0].resourceUrl)??>
                                        <div class="result-item-banner">
                                            <img src="${item.bannerList[0].resourceUrl}${(item.bannerList[0].params)!''}" alt="${item.title}">
                                        </div>
                                    </#if>
                                    <div class="result-item-infor">
                                        <p>${(item.content)!''}</p>
                                    </div>
                                </div>
                            </div>
                        <#elseif item.type == "ARTICLE_TYPE_JOIN_US_SOCIETY">
                            <a href="${Language.lans[lan].url_path}offers/${item.id}.html">
                                <div class="result-item">
                                    <div class="result-item-title">
                                        <h2><span class="label">${Language.lans[lan].search_type_society}</span>${(item.title)!'无'}</h2>
                                    </div>
                                    <div class="result-item-data">
                                        <#if (item.bannerList[0].resourceUrl)??>
                                            <div class="result-item-banner">
                                                <img src="${item.bannerList[0].resourceUrl}${(item.bannerList[0].params)!''}" alt="${item.title}">
                                            </div>
                                        </#if>
                                        <div class="result-item-infor">
                                            <p>${(item.brief)!''}</p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        <#elseif item.type == "ARTICLE_TYPE_JOIN_US_SCHOOL">
                            <a href="${item.action}">
                                <div class="result-item">
                                    <div class="result-item-title">
                                        <h2><span class="label">${Language.lans[lan].search_type_school}</span>${(item.title)!'无'}</h2>
                                    </div>
                                    <div class="result-item-data">
                                        <#if (item.bannerList[0].resourceUrl)??>
                                            <div class="result-item-banner">
                                                <img src="${item.bannerList[0].resourceUrl}${(item.bannerList[0].params)!''}" alt="${item.title}">
                                            </div>
                                        </#if>
                                        <div class="result-item-infor">
                                            <p>${(item.brief)!''}</p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        <#elseif item.type == "ARTICLE_TYPE_COMPANY_PARTY">
                            <a href="${Language.lans[lan].url_path}partys/${item.id}.html">
                                <div class="result-item">
                                    <div class="result-item-title">
                                        <h2><span class="label">${Language.lans[lan].search_type_partys}</span>${(item.title)!'无'}</h2>
                                    </div>
                                    <div class="result-item-data">
                                        <#if (item.bannerList[0].resourceUrl)??>
                                            <div class="result-item-banner">
                                                <img src="${item.bannerList[0].resourceUrl}${(item.bannerList[0].params)!''}" alt="${item.title}">
                                            </div>
                                        </#if>
                                        <div class="result-item-infor">
                                            <p>${(item.brief)!''}</p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        <#else>
                            <a href="${item.action}">
                                <div class="result-item">
                                    <div class="result-item-title">
                                        <h2><span class="label">${Language.lans[lan].search_type_link}</span>${(item.title)!'无'}</h2>
                                    </div>
                                    <div class="result-item-data">
                                        <#if (item.bannerList[0].resourceUrl)??>
                                            <div class="result-item-banner">
                                                <img src="${item.bannerList[0].resourceUrl}${(item.bannerList[0].params)!''}" alt="${item.title}">
                                            </div>
                                        </#if>
                                        <div class="result-item-infor">
                                            <p>${(item.brief)!''}</p>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </#if>

                    </#list>
                </div>
                <div class="result-none">
                    <img src="http://img.bnibt.com/static/img_none.png" alt="">
                    <p>${Language.lans[lan].products_no_data}</p>
                </div>
            </div>
        </div>

    </div>
</@base.base_container>