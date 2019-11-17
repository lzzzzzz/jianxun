<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import 'common/language.ftl' as Language>
<@base.base_container
    title="${Language.lans[lan].news_title}"
    headers=[
        "/css/breadcrumb.css",
        "/css/news.css"
    ]
    scripts=[
        "/js/news.js"
    ]>
    <div class="news">
        <img class="banner hidden-xs" src="http://img.bnibt.com/static/banner_news.png" alt="">
        <img class="banner visible-xs-block" src="http://img.bnibt.com/static/banner_news.png?x-oss-process=image/crop,w_750,h_300,g_center" alt="">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "${Language.lans[lan].url_path}news.html",
        "zh": "新闻中心",
        "en": "News"
        }] />
        <div class="news-list">
            <div class="container">
                <div class="news-items">
                    <#if news?? && news?size gt 0>
                        <#list news as new>
                            <a hidefocus="true" href="${Language.lans[lan].url_path}news/${new.id}.html" title="${new.title}" class="news-item">
                                <img src="${(new.bannerList[0].resourceUrl)!''}${(new.bannerList[0].params)!''}" alt="">
                                <h2 class="news-title">
                                    <strong>${new.title?html}</strong>
                                </h2>
                                <p class="news-content">
                                    ${(new.brief)!''}
                                </p>
                            </a>
                        </#list>
                    <#else>
                        <div class="no-news">
                            <img src="http://img.bnibt.com/static/img_none.png" alt="">
                            <p>${Language.lans[lan].products_no_data}</p>
                        </div>
                    </#if>
                </div>
                <div class="product-recommend visible-md-block visible-lg-block">
                    <#list products as product>
                        <a class="re-item" href="${Language.lans[lan].url_path}products/${product.id}.html" title="${product.title}">
                            <img src="${(product.bannerList[0].resourceUrl)!''}${(product.bannerList[0].params)!''}" alt="">
                            <h3><strong>${product.title}</strong></h3>
                        </a>
                    </#list>
                </div>
            </div>
        </div>
    <#if news?? && news?size gt 0>
        <div class="mobile-more news-more visible-xs-block">
            <a href="#" class="btn previous">上一页</a>
            <a href="#" class="btn next">下一页</a>
        </div>
    </#if>
        <div class="news-pages hidden-xs">
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