<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import 'common/language.ftl' as Language>
<@base.base_container
title="${Language.lans[lan].partys_title}"
headers=[
"/css/breadcrumb.css",
"/css/partys.css"
]
scripts=[
"/js/partys.js"
]>
    <div class="partys">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "#",
        "zh": "关于我们",
        "en": "About Us"
        }, {
        "url": "${Language.lans[lan].url_path}partys.html",
        "zh": "党建工作",
        "en": "Partys"
        }] />
        <div class="partys-list">
            <div class="container">
                <div class="partys-items">
                    <#if partys?? && partys?size gt 0>
                        <#list partys as new>
                            <a hidefocus="true" href="${Language.lans[lan].url_path}partys/${new.id}.html" title="${new.title}" class="partys-item">
                                <img src="${(new.bannerList[0].resourceUrl)!''}${(new.bannerList[0].params)!''}" alt="">
                                <h2 class="partys-title">
                                    <strong>${new.title?html}</strong>
                                </h2>
                                <p class="partys-content">
                                    ${(new.brief)!''}
                                </p>
                            </a>
                        </#list>
                    <#else>
                        <div class="no-partys">
                            <img src="http://img.bnibt.com/static/img_none.png" alt="">
                            <p>${Language.lans[lan].products_no_data}</p>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
        <#if partys?? && partys?size gt 0>
            <div class="mobile-more partys-more visible-xs-block">
                <a href="#" class="btn previous">上一页</a>
                <a href="#" class="btn next">下一页</a>
            </div>
        </#if>
        <div class="partys-pages hidden-xs">
            <div class="container">
                <nav aria-label="Page navigation">
                    <ul class="pagination" content="${page.page?c}/${page.totalPage?c}/${page.limit?c}/${page.totalItem?c}">
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
            <div class="product-pages-mark"></div>
        </div>
    </div>

</@base.base_container>